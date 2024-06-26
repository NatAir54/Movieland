package com.nataliia.koval.movieland.service.cache.impl;

import com.nataliia.koval.movieland.AbstractBaseITest;
import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.repository.GenreRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import java.util.List;

@SpringBootTest
@SqlGroup({
        @Sql(scripts = "classpath:db/initialize_genre_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/remove_genre_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class DefaultGenreCacheITest extends AbstractBaseITest {
    @Autowired
    private DefaultGenreCache genreCache;

    @Autowired
    private GenreRepository genreRepository;


    @Test
    @DisplayName("Cache initialization should initialize cache with genres from the database")
    void testCacheInitialization() {
        List<GenreDto> cachedGenres = genreCache.getAll();

        Assertions.assertEquals(15, cachedGenres.size());
        Assertions.assertEquals("драма", cachedGenres.get(0).getName());
        Assertions.assertEquals("криминал", cachedGenres.get(1).getName());
        Assertions.assertEquals("фэнтези", cachedGenres.get(2).getName());
        Assertions.assertEquals("детектив", cachedGenres.get(3).getName());
        Assertions.assertEquals("мелодрама", cachedGenres.get(4).getName());
        Assertions.assertEquals("биография", cachedGenres.get(5).getName());
        Assertions.assertEquals("комедия", cachedGenres.get(6).getName());
        Assertions.assertEquals("фантастика", cachedGenres.get(7).getName());
    }

    @Test
    @SneakyThrows
    @DisplayName("Cache should update after the specified interval")
    void testCacheUpdate() {
        List<GenreDto> initialCachedGenres = genreCache.getAll();

        Thread.sleep(5000);

        List<GenreDto> updatedCachedGenres = genreCache.getAll();

        Assertions.assertNotSame(initialCachedGenres, updatedCachedGenres);
    }

    @Test
    @DisplayName("Cache should remain consistent between serial calls within update interval")
    void testCacheConsistency() {
        List<GenreDto> cachedGenres1 = genreCache.getAll();
        List<GenreDto> cachedGenres2 = genreCache.getAll();

        Assertions.assertEquals(cachedGenres1, cachedGenres2);
    }
}