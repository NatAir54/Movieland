package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.service.MovieSortingService;
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
    private final MovieSortingService movieSortingService;


    @Override
    public List<MovieDto> findAll(String ratingOrder, String priceOrder) {
        List<Movie> movies;
        if (ratingOrder == null && priceOrder == null) {
            movies = movieRepository.findAll();
        } else {
            movies = movieSortingService.findAllSorted(ratingOrder, priceOrder);
        }
        return mapToDto(movies);
    }

    @Override
    public List<MovieDto> findThreeRandom() {
        return mapToDto(movieRepository.findThreeRandom());
    }

    @Override
    public List<MovieDto> findByGenre(int genreId, String ratingOrder, String priceOrder) {
        List<Movie> movies;
        if (ratingOrder == null && priceOrder == null) {
            movies = findByGenre(genreId);
        } else {
            movies = movieSortingService.findByGenreSorted(genreId, ratingOrder, priceOrder);
        }
        return mapToDto(movies);
    }

    @Override
    public MovieDto findById(int movieId, CurrencySupported currency) {
        MovieDto movieDto = findById(movieId);

        if (currency == CurrencySupported.UAH) {
            return movieDto;
        }

        double convertedPrice = currencyConverter.convert(movieDto.getPrice(), currency);
        movieDto.setPrice(convertedPrice);

        return movieDto;
    }

    private MovieDto findById(int movieId) {
        return movieRepository.findById(movieId)
                .map(movieMapper::toMovieDto)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }

    private List<Movie> findByGenre(int genreId) {
        return genreRepository.findById(genreId)
                .map(genre -> movieRepository.findByGenre(genre.getId()))
                .orElseThrow(() -> new GenreNotFoundException(genreId));
    }

    private List<MovieDto> mapToDto(List<Movie> movies) {
        return movieMapper.toMovieDtoList((movies));
    }
}