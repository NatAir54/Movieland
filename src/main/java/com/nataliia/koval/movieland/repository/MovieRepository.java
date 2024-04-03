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
    @Override
    List<Movie> findAll();

    @Query(value = "SELECT * FROM movie ORDER BY RANDOM() LIMIT 3", nativeQuery = true)
    List<Movie> findThreeRandom();

    @Query(value = "SELECT m.* FROM movie m " +
            "INNER JOIN movie_genre mg ON m.id = mg.movie_id " +
            "WHERE mg.genre_id = :genreId", nativeQuery = true)
    List<Movie> findByGenre(@Param("genreId") int genreId);
}
