package com.nataliia.koval.movieland.service;

import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.entity.Genre;

import java.util.List;

/**
 * Service interface for retrieving genres.
 * Provides a method to fetch all available genres.
 */
public interface GenreService {
    /**
     * Retrieves all genres.
     *
     * @return List of GenreDto representing all available genres.
     */
    List<GenreDto> findAll();
}
