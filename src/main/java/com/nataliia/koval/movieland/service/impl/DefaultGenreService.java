package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.cache.GenreCache;
import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.mapper.GenreMapper;
import com.nataliia.koval.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DefaultGenreService implements GenreService {
    private final GenreCache genreCache;
    private final GenreMapper genreMapper;

    @Override
    public List<GenreDto> findAll() {
        return genreCache.retrieveGenresFromCache().stream()
            .map(genreMapper::toGenreDto)
            .collect(Collectors.toList());
    }
}
