package com.nataliia.koval.movieland.repository;

import com.nataliia.koval.movieland.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


/**
 * Repository interface for managing {@link Movie} entities.
 * Extends JpaRepository for standard CRUD operations.
 */
public interface MovieBaseRepository extends JpaRepository<Movie, Integer> {

    /**
     * Retrieves a list of three random movies.
     *
     * @return a list of three randomly selected movies
     */
    @Query(value = "SELECT * FROM movie ORDER BY RANDOM() LIMIT 3", nativeQuery = true)
    List<Movie> findThreeRandom();
}
