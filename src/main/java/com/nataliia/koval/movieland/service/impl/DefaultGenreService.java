package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.service.cache.GenreCache;
import com.nataliia.koval.movieland.service.cache.ImmutableGenre;
import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.mapper.GenreMapper;
import com.nataliia.koval.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class DefaultGenreService implements GenreService {
    private final GenreCache genreCache;
    private final GenreMapper genreMapper;

    @Override
    public List<GenreDto> findAll() {
        return mapToDtoList(genreCache.getAll());
    }

    private List<GenreDto> mapToDtoList(List<ImmutableGenre> genres) {
        return genreMapper.toDtoList(genres);
    }
}
