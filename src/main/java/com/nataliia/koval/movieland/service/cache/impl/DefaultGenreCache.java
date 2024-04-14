package com.nataliia.koval.movieland.service.cache.impl;

import com.nataliia.koval.movieland.service.cache.Cache;
import com.nataliia.koval.movieland.service.cache.GenreCache;
import com.nataliia.koval.movieland.service.cache.ImmutableGenre;
import com.nataliia.koval.movieland.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Primary
@RequiredArgsConstructor
@Cache
public class DefaultGenreCache implements GenreCache {

    private final GenreRepository genreRepository;
    private final CopyOnWriteArrayList<ImmutableGenre> genresCache = new CopyOnWriteArrayList<>();

    @PostConstruct
    void initCache() {
        if(genresCache.isEmpty()) {
            genresCache.addAll(fetchGenresFromRepository());
        }
    }

    @Override
    public List<ImmutableGenre> getAll() {
        return List.copyOf(genresCache);
    }

    @Scheduled(cron = "${cache.genres.update.schedule}")
    void updateCache() {
        genresCache.clear();
        genresCache.addAll(fetchGenresFromRepository());
    }

    private List<ImmutableGenre> fetchGenresFromRepository() {
        return new ArrayList<>(genreRepository.findAll());
    }
}
