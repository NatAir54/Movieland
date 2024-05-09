package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.repository.impl.DefaultMovieCustomRepository;
import com.nataliia.koval.movieland.service.MovieSortingService;
import com.nataliia.koval.movieland.service.conversion.CurrencyConverter;
import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;
import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.exception.GenreNotFoundException;
import com.nataliia.koval.movieland.exception.MovieNotFoundException;
import com.nataliia.koval.movieland.mapper.MovieMapper;
import com.nataliia.koval.movieland.repository.GenreRepository;
import com.nataliia.koval.movieland.repository.MovieBaseRepository;
import com.nataliia.koval.movieland.service.MovieService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultMovieService implements MovieService {
    private final MovieSortingService movieSortingService;
    private final CurrencyConverter currencyConverter;

    private final MovieBaseRepository movieBaseRepository;
    private final DefaultMovieCustomRepository movieCustomRepositoryImpl;
    private final GenreRepository genreRepository;
    private final MovieMapper movieMapper;


    @Transactional
    @Override
    public List<MovieDto> findAll(String ratingOrder, String priceOrder) {
        List<Movie> movies = (ratingOrder == null && priceOrder == null) ?
                movieBaseRepository.findAll() :
                movieSortingService.findAllSortByPriceOrRating(ratingOrder, priceOrder);
        return mapToDtoList(movies);
    }

    @Transactional
    @Override
    public List<MovieDto> findThreeRandom() {
        return mapToDtoList(movieBaseRepository.findThreeRandom());
    }

    @Transactional
    @Override
    public List<MovieDto> findByGenre(int genreId, String ratingOrder, String priceOrder) {
        List<Movie> movies = (ratingOrder == null && priceOrder == null) ?
                findByGenre(genreId) :
                movieSortingService.findByGenreSortByPriceOrRating(genreId, ratingOrder, priceOrder);
        return mapToDtoList(movies);
    }

    @Override
    public MovieDto findById(int movieId, CurrencySupported currency) {
        MovieDto movieDto = findById(movieId);

        if (currency != CurrencySupported.UAH) {
            double convertedPrice = currencyConverter.convert(movieDto.getPrice(), currency);
            double priceRoundedToTwoDecimals = new BigDecimal(convertedPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
            movieDto.setPrice(priceRoundedToTwoDecimals);
        }

        return movieDto;
    }

    private MovieDto findById(int movieId) {
        return movieBaseRepository.findById(movieId)
                .map(movieMapper::toDto)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }

    private List<Movie> findByGenre(int genreId) {
        return genreRepository.findById(genreId)
                .map(genre -> movieCustomRepositoryImpl.findByGenre(genre.getId()))
                .orElseThrow(() -> new GenreNotFoundException(genreId));
    }

    private List<MovieDto> mapToDtoList(List<Movie> movies) {
        return movieMapper.toDtoList((movies));
    }
}