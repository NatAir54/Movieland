package com.nataliia.koval.movieland.cache.impl;

import com.nataliia.koval.movieland.entity.Genre;
import com.nataliia.koval.movieland.cache.ImmutableGenre;
import com.nataliia.koval.movieland.repository.GenreRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.time.Duration.ofSeconds;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenreCacheImplTest {
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private GenreCacheImpl genreCache;

    @Test
    @DisplayName("needsCacheUpdate should return true when cache is not fresh")
    public void needsCacheUpdate_ShouldReturnTrueWhenCacheIsNotFresh() {
        genreCache.setLastUpdate(Instant.now().minus(Duration.ofHours(5)));
        assertTrue(genreCache.needsCacheUpdate());
    }

    @Test
    @DisplayName("needsCacheUpdate should return false when cache is fresh")
    public void needsCacheUpdate_ShouldReturnFalseWhenCacheIsFresh() {
        genreCache.setLastUpdate(Instant.now().minus(Duration.ofHours(2)));
        assertFalse(genreCache.needsCacheUpdate());
    }

    @Test
    @DisplayName("retrieveGenresFromCache should update cache and return genres when cache is not fresh")
    public void retrieveGenresFromCache_ShouldUpdateCacheAndReturnGenresWhenCacheIsNotFresh() {
        List<Genre> freshGenres = createGenre();
        when(genreRepository.findAll()).thenReturn(freshGenres);

        genreCache.setLastUpdate(Instant.now().minus(Duration.ofHours(5)));

        List<ImmutableGenre> retrievedGenres = genreCache.retrieveGenresFromCache();

        assertEquals(freshGenres.size(), retrievedGenres.size());

        IntStream.range(0, freshGenres.size())
                .forEach(i -> {
                    Genre freshGenre = freshGenres.get(i);
                    ImmutableGenre retrievedGenre = retrievedGenres.get(i);
                    assertEquals(freshGenre.getId(), retrievedGenre.getId());
                    assertEquals(freshGenre.getName(), retrievedGenre.getName());
                });
    }

    @Test
    @DisplayName("scheduleCacheUpdate should trigger cache update periodically")
    public void scheduleCacheUpdate_ShouldTriggerCacheUpdatePeriodically() {
        List<Genre> freshGenres = createGenre();
        when(genreRepository.findAll()).thenReturn(freshGenres);

        genreCache.scheduleCacheUpdate();

        await().atMost(ofSeconds(5)).untilAsserted(() -> {
            List<ImmutableGenre> retrievedGenres = genreCache.retrieveGenresFromCache();
            assertEquals(freshGenres.size(), retrievedGenres.size());

            IntStream.range(0, freshGenres.size())
                    .forEach(i -> {
                        Genre freshGenre = freshGenres.get(i);
                        ImmutableGenre retrievedGenre = retrievedGenres.get(i);
                        assertEquals(freshGenre.getId(), retrievedGenre.getId());
                        assertEquals(freshGenre.getName(), retrievedGenre.getName());
                    });
        });
    }

    @Test
    @DisplayName("updateCache should fetch genres from database and update cache")
    public void updateCache_ShouldFetchGenresFromDatabaseAndUpdateCache() {
        List<Genre> freshGenres = createGenre();
        when(genreRepository.findAll()).thenReturn(freshGenres);

        genreCache.updateCache();

        List<ImmutableGenre> immutableFreshGenres = freshGenres.stream()
                .map(genre -> (ImmutableGenre) genre)
                .collect(Collectors.toList());

        assertEquals(immutableFreshGenres, genreCache.retrieveGenresFromCache());
    }

    private List<Genre> createGenre() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1, "Action"));
        genres.add(new Genre(2, "Drama"));
        genres.add(new Genre(3, "Comedy"));
        return genres;
    }
}
