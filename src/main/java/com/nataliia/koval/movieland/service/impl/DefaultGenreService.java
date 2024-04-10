package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.cache.GenreCache;
import com.nataliia.koval.movieland.cache.ImmutableGenre;
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
        return mapGenresToDto(genreCache.retrieveGenresFromCache());
    }

    private List<GenreDto> mapGenresToDto(List<ImmutableGenre> genres) {
        return genreMapper.toGenreDtoList(genres);
    }
}
