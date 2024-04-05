package com.nataliia.koval.movieland.cache.impl;

import com.nataliia.koval.movieland.entity.Genre;
import com.nataliia.koval.movieland.cache.ImmutableGenre;
import com.nataliia.koval.movieland.repository.GenreRepository;
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
    @DisplayName("isCacheStale should return true when cache is stale")
    public void isCacheStale_ShouldReturnTrueWhenCacheIsStale() {
        Instant lastUpdate = Instant.now().minus(Duration.ofHours(5));
        genreCache.setLastUpdate(lastUpdate);
        assertTrue(genreCache.isCacheStale());
    }

    @Test
    @DisplayName("isCacheStale should return false when cache is fresh")
    public void isCacheStale_ShouldReturnFalseWhenCacheIsFresh() {
        Instant lastUpdate = Instant.now().minus(Duration.ofHours(2));
        genreCache.setLastUpdate(lastUpdate);
        assertFalse(genreCache.isCacheStale());
    }

    @Test
    @DisplayName("retrieveGenresFromCache should update cache and return genres when cache is stale")
    public void retrieveGenresFromCache_ShouldUpdateCacheAndReturnGenresWhenCacheIsStale() {
        List<Genre> freshGenres = createGenre();
        when(genreRepository.findAll()).thenReturn(freshGenres);

        Instant lastUpdate = Instant.now().minus(Duration.ofHours(5));
        genreCache.setLastUpdate(lastUpdate);

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
    @DisplayName("scheduleCacheUpdate should update cache periodically")
    public void scheduleCacheUpdate_ShouldUpdateCachePeriodically() {
        List<Genre> freshGenres = createGenre();
        when(genreRepository.findAll()).thenReturn(freshGenres);

        Instant lastUpdate = Instant.now().minus(Duration.ofHours(5));
        genreCache.setLastUpdate(lastUpdate);

        genreCache.scheduleCacheUpdate();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<ImmutableGenre> retrievedGenres = genreCache.retrieveGenresFromCache();
        assertEquals(freshGenres, retrievedGenres);
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
