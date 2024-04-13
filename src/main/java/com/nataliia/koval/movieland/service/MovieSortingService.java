package com.nataliia.koval.movieland.service;

import com.nataliia.koval.movieland.entity.Movie;

import java.util.List;

/**
 * Service interface for sorting movies.
 */
public interface MovieSortingService {

    /**
     * Retrieves all movies sorted according to the provided rating and price order.
     *
     * @param ratingOrder The order in which movies should be sorted by rating. Can be "asc" for ascending or "desc" for descending. Null if no sorting by rating is required.
     * @param priceOrder  The order in which movies should be sorted by price. Can be "asc" for ascending or "desc" for descending. Null if no sorting by price is required.
     * @return A list of movies sorted according to the specified criteria.
     */
    List<Movie> findAllSorted(String ratingOrder, String priceOrder);

    /**
     * Retrieves movies of a specific genre sorted according to the provided rating and price order.
     *
     * @param genreId     The ID of the genre for which movies should be retrieved.
     * @param ratingOrder The order in which movies should be sorted by rating. Can be "asc" for ascending or "desc" for descending. Null if no sorting by rating is required.
     * @param priceOrder  The order in which movies should be sorted by price. Can be "asc" for ascending or "desc" for descending. Null if no sorting by price is required.
     * @return A list of movies belonging to the specified genre, sorted according to the specified criteria.
     */
    List<Movie> findByGenreSorted(int genreId, String ratingOrder, String priceOrder);
}
