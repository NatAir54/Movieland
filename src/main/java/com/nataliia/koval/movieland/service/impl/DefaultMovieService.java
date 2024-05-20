package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.entity.Country;
import com.nataliia.koval.movieland.entity.Genre;
import com.nataliia.koval.movieland.repository.CountryRepository;
import com.nataliia.koval.movieland.repository.MovieCustomRepository;
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
import com.nataliia.koval.movieland.web.controller.entity.MovieAddRequest;
import com.nataliia.koval.movieland.web.controller.entity.MovieEditRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DefaultMovieService implements MovieService {
    private final MovieSortingService movieSortingService;
    private final CurrencyConverter currencyConverter;

    private final MovieBaseRepository movieBaseRepository;
    private final MovieCustomRepository movieCustomRepository;
    private final GenreRepository genreRepository;
    private final CountryRepository countryRepository;
    private final MovieMapper movieMapper;


    @Override
    public List<MovieDto> findAll(String ratingOrder, String priceOrder) {
        List<Movie> movies = (ratingOrder == null && priceOrder == null) ?
                movieBaseRepository.findAll() :
                movieSortingService.findAllSortByPriceOrRating(ratingOrder, priceOrder);
        return mapToDtoList(movies);
    }

    @Override
    public List<MovieDto> findThreeRandom() {
        return mapToDtoList(movieBaseRepository.findThreeRandom());
    }

    @Override
    public List<MovieDto> findByGenre(int genreId, String ratingOrder, String priceOrder) {
        List<Movie> movies = (ratingOrder == null && priceOrder == null) ?
                findByGenre(genreId) :
                movieSortingService.findByGenreSortByPriceOrRating(genreId, ratingOrder, priceOrder);
        return mapToDtoList(movies);
    }

    @Override
    public MovieDto findById(int movieId, CurrencySupported currency) {
        MovieDto movieDto = movieMapper.toDto(findById(movieId));

        if (currency != CurrencySupported.UAH) {
            double convertedPrice = currencyConverter.convert(movieDto.getPrice(), currency);
            double priceRoundedToTwoDecimals = new BigDecimal(convertedPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
            movieDto.setPrice(priceRoundedToTwoDecimals);
        }

        return movieDto;
    }

    @Override
    public Movie findById(int movieId) {
        return movieBaseRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }

    @Transactional
    @Override
    public MovieDto addMovie(MovieAddRequest movieAddRequest) {
        Movie movie = new Movie();
        movie.setNameRussian(movieAddRequest.nameRussian());
        movie.setNameNative(movieAddRequest.nameNative());
        movie.setYearOfRelease(movieAddRequest.yearOfRelease());
        movie.setDescription(movieAddRequest.description());
        movie.setPrice(movieAddRequest.price());
        movie.setPicturePath(movieAddRequest.picturePath());
        movie.setCountries(getCountriesByIds(movieAddRequest.countries()));
        movie.setGenres(getGenresByIds(movieAddRequest.genres()));

        Movie savedMovie = movieBaseRepository.save(movie);
        return movieMapper.toDto(savedMovie);
    }

    @Transactional
    @Override
    public MovieDto editMovie(int movieId, MovieEditRequest movieEditRequest) {
        Movie movie = findById(movieId);

        movie.setNameRussian(movieEditRequest.nameRussian());
        movie.setNameNative(movieEditRequest.nameNative());
        movie.setPicturePath(movieEditRequest.picturePath());
        movie.setCountries(getCountriesByIds(movieEditRequest.countries()));
        movie.setGenres(getGenresByIds(movieEditRequest.genres()));

        return movieMapper.toDto(movie);
    }

    private List<Movie> findByGenre(int genreId) {
        return genreRepository.findById(genreId)
                .map(genre -> movieCustomRepository.findByGenre(genre.getId()))
                .orElseThrow(() -> new GenreNotFoundException(genreId));
    }

    private List<MovieDto> mapToDtoList(List<Movie> movies) {
        return movieMapper.toDtoList((movies));
    }

    private Set<Country> getCountriesByIds(List<Integer> countryIds) {
        return new HashSet<>(countryRepository.findAllById(countryIds));
    }

    private Set<Genre> getGenresByIds(List<Integer> genreIds) {
        return new HashSet<>(genreRepository.findAllById(genreIds));
    }
}