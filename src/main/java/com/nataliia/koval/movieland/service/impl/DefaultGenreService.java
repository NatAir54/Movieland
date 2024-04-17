package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.service.cache.GenreCache;
import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class DefaultGenreService implements GenreService {
    private final GenreCache genreCache;

    @Override
    public List<GenreDto> findAll() {
        return genreCache.getAll();
    }

}
