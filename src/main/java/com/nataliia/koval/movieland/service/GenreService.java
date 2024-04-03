package com.nataliia.koval.movieland.service;

import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.entity.Genre;

import java.util.List;

/**
 * Service interface for managing {@link Genre} entities.
 */
public interface GenreService {
    List<GenreDto> findAll();
}
