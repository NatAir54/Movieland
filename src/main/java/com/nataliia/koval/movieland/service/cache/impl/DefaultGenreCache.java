package com.nataliia.koval.movieland.service.cache.impl;

import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.mapper.GenreMapper;
import com.nataliia.koval.movieland.service.cache.Cache;
import com.nataliia.koval.movieland.service.cache.GenreCache;
import com.nataliia.koval.movieland.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Cache
public class DefaultGenreCache implements GenreCache {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private List<SoftReference<GenreDto>> genresCache = new ArrayList<>();

    @PostConstruct
    void initCache() {
        List<GenreDto> fetchedGenres = fetchGenresFromRepository();
        for (GenreDto genre : fetchedGenres) {
            genresCache.add(new SoftReference<>(genre));
        }
    }

    @Override
    public List<GenreDto> getAll() {
        List<GenreDto> result = new ArrayList<>();
        for (SoftReference<GenreDto> softRef : genresCache) {
            GenreDto genre = softRef.get();
            if (genre != null) {
                result.add(genre);
            }
        }
        return result;
    }

    @Scheduled(fixedDelayString = "${cache.genres.update.schedule}")
    void updateCache() {
        List<GenreDto> fetchedGenres = fetchGenresFromRepository();
        List<SoftReference<GenreDto>> genresUpdated = new ArrayList<>();
        for (GenreDto genre : fetchedGenres) {
            genresUpdated.add(new SoftReference<>(genre));
        }
        genresCache = genresUpdated;
    }

    private List<GenreDto> fetchGenresFromRepository() {
        return genreMapper.toDtoList(genreRepository.findAll());
    }
}
