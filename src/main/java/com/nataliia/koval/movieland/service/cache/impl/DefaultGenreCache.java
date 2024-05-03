package com.nataliia.koval.movieland.service.cache.impl;

import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.mapper.GenreMapper;
import com.nataliia.koval.movieland.service.cache.Cache;
import com.nataliia.koval.movieland.service.cache.GenreCache;
import com.nataliia.koval.movieland.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Cache
public class DefaultGenreCache implements GenreCache {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private volatile List<GenreDto> genresCache;

    @PostConstruct
    void initCache() {
        updateCache();
    }

    @Override
    public List<GenreDto> getAll() {
        return new ArrayList<>(genresCache);
    }

    @Scheduled(fixedDelayString = "${cache.genres.update.schedule}")
    void updateCache() {
        genresCache = fetchGenresFromRepository();
    }

    private List<GenreDto> fetchGenresFromRepository() {
        return genreMapper.toDtoList(genreRepository.findAll());
    }
}
