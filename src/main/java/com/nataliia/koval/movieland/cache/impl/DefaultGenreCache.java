package com.nataliia.koval.movieland.cache.impl;

import com.nataliia.koval.movieland.cache.GenreCache;
import com.nataliia.koval.movieland.cache.ImmutableGenre;
import com.nataliia.koval.movieland.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Primary
@RequiredArgsConstructor
@Component
public class DefaultGenreCache implements GenreCache {

    private final GenreRepository genreRepository;
    private final CopyOnWriteArrayList<ImmutableGenre> cache = new CopyOnWriteArrayList<>();

    @PostConstruct
    void initCache() {
        if(cache.isEmpty()) {
            cache.addAll(fetchGenresFromDatabase());
        }
    }

    @Override
    public List<ImmutableGenre> retrieveGenresFromCache() {
        return List.copyOf(cache);
    }

    @Scheduled(cron = "${cache.interval}")
    void updateCache() {
        cache.clear();
        cache.addAll(fetchGenresFromDatabase());
    }

    private List<ImmutableGenre> fetchGenresFromDatabase() {
        return new ArrayList<>(genreRepository.findAll());
    }
}
