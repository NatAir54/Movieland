package com.nataliia.koval.movieland.repository;

import com.nataliia.koval.movieland.entity.Movie;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Repository interface for managing {@link Movie} entities.
 * Extends JpaRepository for standard CRUD operations.
 */
@NonNullApi
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    /**
     * Retrieves a list of three random movies.
     *
     * @return a list of three randomly selected movies
     */
    @Query(value = "SELECT * FROM movie ORDER BY RANDOM() LIMIT 3", nativeQuery = true)
    List<Movie> findThreeRandom();

    /**
     * Retrieves movies by a given genre ID.
     *
     * @param genreId Genre ID to filter movies by.
     * @return List of movies belonging to the specified genre.
     */
    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId")
    List<Movie> findByGenre(@Param("genreId") int genreId);

    /**
     * Retrieves movies by a given genre ID, sorted by rating in ascending order.
     *
     * @param genreId Genre ID to filter movies by.
     * @return List of movies belonging to the specified genre, sorted by rating in ascending order.
     */
    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId ORDER BY m.rating ASC")
    List<Movie> findByGenreSortedByRatingAsc(@Param("genreId") int genreId);

    /**
     * Retrieves movies by a given genre ID, sorted by rating in descending order.
     *
     * @param genreId Genre ID to filter movies by.
     * @return List of movies belonging to the specified genre, sorted by rating in descending order.
     */
    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId ORDER BY m.rating DESC")
    List<Movie> findByGenreSortedByRatingDesc(@Param("genreId") int genreId);

    /**
     * Retrieves movies by a given genre ID, sorted by price in ascending order.
     *
     * @param genreId Genre ID to filter movies by.
     * @return List of movies belonging to the specified genre, sorted by price in ascending order.
     */
    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId ORDER BY m.price ASC")
    List<Movie> findByGenreSortedByPriceAsc(@Param("genreId") int genreId);

    /**
     * Retrieves movies by a given genre ID, sorted by price in descending order.
     *
     * @param genreId Genre ID to filter movies by.
     * @return List of movies belonging to the specified genre, sorted by price in descending order.
     */
    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId ORDER BY m.price DESC")
    List<Movie> findByGenreSortedByPriceDesc(@Param("genreId") int genreId);
}
