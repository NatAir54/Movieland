package com.nataliia.koval.movieland.cache.impl;

import com.nataliia.koval.movieland.entity.Genre;
import com.nataliia.koval.movieland.repository.GenreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultGenreCacheTest {
    @Mock
    private GenreRepository genreRepository;

    private DefaultGenreCache genreCache;

    @BeforeEach
    public void setUp() {
        genreCache = new DefaultGenreCache(genreRepository);
    }

    @Test
    @DisplayName("updateCache should fetch genres from database and update cache")
    public void updateCache_ShouldFetchGenresFromDatabaseAndUpdateCache() {
        List<Genre> freshGenres = createGenre();
        when(genreRepository.findAll()).thenReturn(freshGenres);

        genreCache.updateCache();

        assertEquals(freshGenres, genreCache.retrieveGenresFromCache());
    }

    @Test
    @DisplayName("initCache should fetch genres from database if cache is empty")
    public void initCache_ShouldFetchGenresFromDatabaseIfCacheIsEmpty() {
        List<Genre> freshGenres = createGenre();
        when(genreRepository.findAll()).thenReturn(freshGenres);

        genreCache.initCache();

        assertEquals(freshGenres, genreCache.retrieveGenresFromCache());
    }

    private List<Genre> createGenre() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1, "Action"));
        genres.add(new Genre(2, "Drama"));
        genres.add(new Genre(3, "Comedy"));
        return genres;
    }
}
