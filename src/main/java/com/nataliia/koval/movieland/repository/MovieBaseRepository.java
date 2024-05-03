package com.nataliia.koval.movieland.repository;

import com.nataliia.koval.movieland.entity.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


/**
 * Repository interface for managing {@link Movie} entities.
 * Extends JpaRepository for standard CRUD operations.
 */
public interface MovieBaseRepository extends JpaRepository<Movie, Integer> {

    @EntityGraph(value = "graph.MovieGenresCountriesReviewsUser", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Movie> findById(int id);


    /**
     * Retrieves a list of three random movies.
     *
     * @return a list of three randomly selected movies
     */
    @Query(value = "SELECT * FROM movie ORDER BY RANDOM() LIMIT 3", nativeQuery = true)
    List<Movie> findThreeRandom();
}
