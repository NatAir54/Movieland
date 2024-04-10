package com.nataliia.koval.movieland.service;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.exception.GenreNotFoundException;

import java.util.List;

/**
 * Service interface for managing movie-related operations.
 * Provides methods to retrieve movies, including sorting and filtering options.
 */
public interface MovieService {
    /**
     * Retrieves all movies with optional sorting by rating or price.
     *
     * @param ratingOrder Sorting order for movie ratings. Can be "asc" for ascending or "desc" for descending.
     * @param priceOrder Sorting order for movie prices. Can be "asc" for ascending or "desc" for descending.
     * @return List of MovieDto representing the movies.
     */
    List<MovieDto> findAll(String ratingOrder, String priceOrder);

    /**
     * Retrieves three random movies.
     *
     * @return List of MovieDto representing the random movies.
     */
    List<MovieDto> findThreeRandom();

    /**
     * Retrieves movies by a specified genre.
     *
     * @param genreId The ID of the genre to filter movies by.
     * @return List of MovieDto representing the movies matching the specified genre.
     * @throws GenreNotFoundException If the specified genre is not found.
     */
    List<MovieDto> findByGenre(int genreId) throws GenreNotFoundException;

    /**
     * Retrieves a movie by its ID.
     *
     * @param movieId The ID of the movie to retrieve.
     * @return MovieDto representing the movie with the specified ID.
     */
    MovieDto findById(int movieId);
}
