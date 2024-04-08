package com.nataliia.koval.movieland.service;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.exception.GenreNotFoundException;

import java.util.List;

/**
 * Service interface for managing {@link Movie} entities.
 */
public interface MovieService {
    List<MovieDto> findAll(String ratingOrder, String priceOrder);
    List<MovieDto> findThreeRandom();
    List<MovieDto> findByGenre(String genreId) throws GenreNotFoundException;
}
