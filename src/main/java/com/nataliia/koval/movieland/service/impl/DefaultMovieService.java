package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.service.conversion.CurrencyConverter;
import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;
import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.exception.GenreNotFoundException;
import com.nataliia.koval.movieland.exception.MovieNotFoundException;
import com.nataliia.koval.movieland.mapper.MovieMapper;
import com.nataliia.koval.movieland.repository.GenreRepository;
import com.nataliia.koval.movieland.repository.MovieRepository;
import com.nataliia.koval.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultMovieService implements MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieMapper movieMapper;
    private final CurrencyConverter currencyConverter;

    @Override
    public List<MovieDto> findAll(String ratingOrder, String priceOrder) {
        if (ratingOrder == null && priceOrder == null) {
            return mapMoviesToDto(movieRepository.findAll());
        }
        return findAllSorted(ratingOrder, priceOrder);
    }

    @Override
    public List<MovieDto> findThreeRandom() {
        return mapMoviesToDto(movieRepository.findThreeRandom());
    }

    @Override
    public List<MovieDto> findByGenre(int genreId) {
        return mapMoviesToDto(findMoviesByGenre(genreId));
    }

    @Override
    public MovieDto findById(int movieId, CurrencySupported currency) {
        MovieDto movieDto = findMovieById(movieId);

        if (currency == CurrencySupported.UAH) {
            return movieDto;
        }

        double convertedPrice = currencyConverter.convert(movieDto.getPrice(), currency);
        movieDto.setPrice(convertedPrice);

        return movieDto;
    }

    private List<MovieDto> findAllSorted(String ratingOrder, String priceOrder) {
        List<Movie> sortedMovies = ratingOrder == null ?
                movieRepository.findAllSortedByPrice(priceOrder) :
                movieRepository.findAllSortedByRating(ratingOrder);
        return mapMoviesToDto(sortedMovies);
    }

    private MovieDto findMovieById(int movieId) {
        return movieRepository.findById(movieId)
                .map(movieMapper::toMovieDto)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }

    private List<Movie> findMoviesByGenre(int genreId) {
        return genreRepository.findById(genreId)
                .map(genre -> movieRepository.findByGenre(genre.getId()))
                .orElseThrow(() -> new GenreNotFoundException(genreId));
    }

    private List<MovieDto> mapMoviesToDto(List<Movie> movies) {
        return movieMapper.toMovieDtoList((movies));
    }
}