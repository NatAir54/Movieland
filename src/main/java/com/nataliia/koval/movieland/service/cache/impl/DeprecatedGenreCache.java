package com.nataliia.koval.movieland.service.cache.impl;

import com.nataliia.koval.movieland.service.cache.GenreCache;
import com.nataliia.koval.movieland.service.cache.ImmutableGenre;
import com.nataliia.koval.movieland.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Deprecated
@RequiredArgsConstructor
@Component
public class DeprecatedGenreCache implements GenreCache {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final GenreRepository genreRepository;
    private List<ImmutableGenre> cache =  new ArrayList<>();


    @PostConstruct
    void initCache() {
        if(cache.isEmpty()) {
            cache.addAll(fetchGenresFromDatabase());
        }
    }

    @Override
    public List<ImmutableGenre> retrieveGenresFromCache() {
        return new ArrayList<>(cache);
    }

    @Scheduled(cron = "${cache.genres_interval}")
    void updateCache() {
        lock.writeLock().lock();
        try {
            this.cache = fetchGenresFromDatabase();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private List<ImmutableGenre> fetchGenresFromDatabase() {
        return new ArrayList<>(genreRepository.findAll());
    }
}