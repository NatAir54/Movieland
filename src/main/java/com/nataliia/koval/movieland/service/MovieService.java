package com.nataliia.koval.movieland.service;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.exception.GenreNotFoundException;
import com.nataliia.koval.movieland.exception.MovieNotFoundException;
import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;

import java.util.List;

/**
 * Service interface for managing movie-related operations.
 * Provides methods to retrieve movies, including sorting and filtering options.
 */
public interface MovieService {

    /**
     * Retrieves all movies with optional sorting by rating or price if provided.
     *
     * @param ratingOrder Optional parameter specifying the sorting order for movie ratings.
     *                    Can be "asc" for ascending or "desc" for descending. If null or empty, no sorting by rating will be applied.
     * @param priceOrder Optional parameter specifying the sorting order for movie prices.
     *                   Can be "asc" for ascending or "desc" for descending. If null or empty, no sorting by price will be applied.
     * @return List of MovieDto representing the movies, optionally sorted by rating and/or price.
     */
    List<MovieDto> findAll(String ratingOrder, String priceOrder);


    /**
     * Retrieves three random movies.
     *
     * @return List of MovieDto representing the random movies.
     */
    List<MovieDto> findThreeRandom();


    /**
     * Retrieves movies by a specified genre, ordered by rating and price if provided.
     *
     * @param genreId     The ID of the genre to filter movies by.
     * @param ratingOrder Optional parameter specifying the sorting order for movie ratings.
     *                    Can be "asc" for ascending or "desc" for descending. If null or empty, no sorting by rating will be applied.
     * @param priceOrder  Optional parameter specifying the sorting order for movie prices.
     *                    Can be "asc" for ascending or "desc" for descending. If null or empty, no sorting by price will be applied.
     * @return List of MovieDto representing the movies matching the specified genre, optionally sorted by rating and/or price.
     * @throws GenreNotFoundException If the specified genre is not found.
     */
    List<MovieDto> findByGenre(int genreId, String ratingOrder, String priceOrder) throws GenreNotFoundException;


    /**
     * Retrieves a movie by its ID and optionally converts its price to the specified currency.
     *
     * @param movieId  The ID of the movie to retrieve.
     * @param currency The currency code to which the price should be converted.
     *                 If null or "UAH", the original price is returned without conversion.
     * @return MovieDto representing the movie with the specified ID.
     * @throws MovieNotFoundException If the specified movie is not found.
     */
    MovieDto findById(int movieId, CurrencySupported currency);

    Movie findById(int movieId);
}
