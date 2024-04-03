package com.nataliia.koval.movieland.repository;

import com.nataliia.koval.movieland.entity.Movie;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing {@link Movie} entities.
 * Extends JpaRepository for standard CRUD operations.
 */
@NonNullApi
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Override
    List<Movie> findAll();

    @Query(value = "SELECT * FROM movie ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<Movie> findRandomMovies(int count);
}
