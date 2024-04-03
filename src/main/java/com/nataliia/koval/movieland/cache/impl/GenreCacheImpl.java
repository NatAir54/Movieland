package com.nataliia.koval.movieland.cache.impl;

import com.nataliia.koval.movieland.cache.GenreCache;
import com.nataliia.koval.movieland.entity.Genre;
import com.nataliia.koval.movieland.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class GenreCacheImpl implements GenreCache {
    private final Lock lock = new ReentrantLock();
    @Autowired
    private GenreRepository genreRepository;
    private List<Genre> cache =  new ArrayList<>();
    private Instant lastUpdate = Instant.EPOCH;


    public List<Genre> retrieveGenresFromCache() {
        lock.lock();
        try {
            if (isCacheStale()) {
                updateCache();
                scheduleCacheUpdate();
            }
            return Collections.unmodifiableList(cache);
        } finally {
            lock.unlock();
        }
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    void updateCache() {
        List<Genre> genresFromDatabase = fetchGenresFromDatabase();

        lock.lock();
        try {
            this.cache = genresFromDatabase;
            this.lastUpdate = Instant.now();
        } finally {
            lock.unlock();
        }
    }

    boolean isCacheStale() {
        lock.lock();
        try {
            return Duration.between(lastUpdate, Instant.now()).compareTo(Duration.ofHours(4)) >= 0;
        } finally {
            lock.unlock();
        }
    }

    void scheduleCacheUpdate() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updateCache, 0, 4, TimeUnit.HOURS);
        scheduler.schedule(scheduler::shutdown, 4, TimeUnit.HOURS);
    }

    private List<Genre> fetchGenresFromDatabase() {
        return genreRepository.findAll();
    }
}
