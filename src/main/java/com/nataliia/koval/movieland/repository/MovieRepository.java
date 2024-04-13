package com.nataliia.koval.movieland.repository;

import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.exception.InvalidSortingException;
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
    @Override
    List<Movie> findAll();

    @Query(value = "SELECT * FROM movie ORDER BY RANDOM() LIMIT 3", nativeQuery = true)
    List<Movie> findThreeRandom();

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId")
    List<Movie> findByGenre(@Param("genreId") int genreId);

    @Query("SELECT m FROM Movie m ORDER BY m.rating DESC")
    List<Movie> findAllSortedByRatingDesc();

    @Query("SELECT m FROM Movie m ORDER BY m.rating ASC")
    List<Movie> findAllSortedByRatingAsc();

    @Query("SELECT m FROM Movie m ORDER BY m.price DESC")
    List<Movie> findAllSortedByPriceDesc();

    @Query("SELECT m FROM Movie m ORDER BY m.price ASC")
    List<Movie> findAllSortedByPriceAsc();

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId ORDER BY m.rating ASC")
    List<Movie> findByGenreSortedByRatingAsc(@Param("genreId") int genreId);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId ORDER BY m.rating DESC")
    List<Movie> findByGenreSortedByRatingDesc(@Param("genreId") int genreId);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId ORDER BY m.price ASC")
    List<Movie> findByGenreSortedByPriceAsc(@Param("genreId") int genreId);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId ORDER BY m.price DESC")
    List<Movie> findByGenreSortedByPriceDesc(@Param("genreId") int genreId);

    default List<Movie> findAllSortedByRating(String ratingOrder) {
        validateSortingOrder(ratingOrder);
        return "asc".equalsIgnoreCase(ratingOrder) ? findAllSortedByRatingAsc() : findAllSortedByRatingDesc();
    }

    default List<Movie> findAllSortedByPrice(String priceOrder) {
        validateSortingOrder(priceOrder);
        return "asc".equalsIgnoreCase(priceOrder) ? findAllSortedByPriceAsc() : findAllSortedByPriceDesc();
    }

    default List<Movie> findByGenreSortedByRating(int genreId, String ratingOrder){
        validateSortingOrder(ratingOrder);
        return "asc".equalsIgnoreCase(ratingOrder) ?
                findByGenreSortedByRatingAsc(genreId) : findByGenreSortedByRatingDesc(genreId);
    };

    default List<Movie> findByGenreSortedByPrice(int genreId, String priceOrder){
        validateSortingOrder(priceOrder);
        return "asc".equalsIgnoreCase(priceOrder) ?
                findByGenreSortedByPriceAsc(genreId) : findByGenreSortedByPriceDesc(genreId);
    };

    private static void validateSortingOrder(String order) {
        if (!"asc".equalsIgnoreCase(order) && !"desc".equalsIgnoreCase(order)) {
            throw new InvalidSortingException(order);
        }
    }
}
