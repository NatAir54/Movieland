package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.entity.Country;
import com.nataliia.koval.movieland.entity.Genre;
import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.entity.Review;
import com.nataliia.koval.movieland.mapper.MovieMapper;
import com.nataliia.koval.movieland.service.MovieEnrichmentService;
import com.nataliia.koval.movieland.service.conversion.CurrencyConverter;
import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;

@Primary
@RequiredArgsConstructor
@Service
public class ParallelMovieEnrichmentService implements MovieEnrichmentService {
    private final CurrencyConverter currencyConverter;

    private final MovieMapper movieMapper;

    private final ExecutorService executorService = Executors.newCachedThreadPool();


    @Override
    public MovieDto enrichMovie(Movie movie, CurrencySupported currency) {
        Callable<Set<Genre>> genresTask = () -> fetchGenres(movie);
        Callable<Set<Country>> countriesTask = () -> fetchCountries(movie);
        Callable<Set<Review>> reviewsTask = () -> fetchReviews(movie);

        Future<Set<Genre>> genresFuture = executorService.submit(genresTask);
        Future<Set<Country>> countriesFuture = executorService.submit(countriesTask);
        Future<Set<Review>> reviewsFuture = executorService.submit(reviewsTask);

        try {
            Set<Genre> genres = genresFuture.get(5, TimeUnit.SECONDS);
            Set<Country> countries = countriesFuture.get(5, TimeUnit.SECONDS);
            Set<Review> reviews = reviewsFuture.get(5, TimeUnit.SECONDS);
            MovieDto movieDto = movieMapper.toDto(movie, genres, countries, reviews);

            if (currency != CurrencySupported.UAH) {
                double convertedPrice = currencyConverter.convert(movieDto.getPrice(), currency);
                double priceRoundedToTwoDecimals = new BigDecimal(convertedPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
                movieDto.setPrice(priceRoundedToTwoDecimals);
            }

            return movieDto;
        } catch (TimeoutException e) {
            genresFuture.cancel(true);
            countriesFuture.cancel(true);
            reviewsFuture.cancel(true);
            throw new RuntimeException("Timeout while fetching movie details", e);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error while fetching movie details", e);
        }
    }

    private Set<Genre> fetchGenres(Movie movie) {
        return movie.getGenres();
    }

    private Set<Country> fetchCountries(Movie movie) {
        return movie.getCountries();
    }

    private Set<Review> fetchReviews(Movie movie) {
        return movie.getReviews();
    }
}
