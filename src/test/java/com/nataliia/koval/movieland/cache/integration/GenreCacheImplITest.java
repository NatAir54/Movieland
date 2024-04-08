package com.nataliia.koval.movieland.cache.integration;

import com.nataliia.koval.movieland.cache.ImmutableGenre;
import com.nataliia.koval.movieland.cache.impl.GenreCacheImpl;
import com.nataliia.koval.movieland.repository.GenreRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(scripts = "classpath:db/initialize_genre_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/remove_genre_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class GenreCacheImplITest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.3");

    @Autowired
    private GenreCacheImpl genreCache;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Cache initialization should initialize cache with genres from the database")
    void testCacheInitialization() {
        List<ImmutableGenre> cachedGenres = genreCache.retrieveGenresFromCache();

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
        List<ImmutableGenre> initialCachedGenres = genreCache.retrieveGenresFromCache();

        // Sleep for the cache update interval plus some buffer time
        Thread.sleep(5000);

        List<ImmutableGenre> updatedCachedGenres = genreCache.retrieveGenresFromCache();

        // Ensure that the cache has been updated
        Assertions.assertNotSame(initialCachedGenres, updatedCachedGenres);
    }

    @Test
    @DisplayName("Cache should remain consistent between serial calls within update interval")
    void testCacheConsistency() {
        // Retrieve genres from the cache multiple times
        List<ImmutableGenre> cachedGenres1 = genreCache.retrieveGenresFromCache();
        List<ImmutableGenre> cachedGenres2 = genreCache.retrieveGenresFromCache();

        // Ensure that the cached genres are consistent between serial calls
        Assertions.assertEquals(cachedGenres1, cachedGenres2);
    }

    @Test
    @DisplayName("Cache should expire and update when last update time is outdated")
    void testCacheExpiry() {
        List<ImmutableGenre> initialCachedGenres = genreCache.retrieveGenresFromCache();

        // Invalidate the cache by setting the last update time to an old timestamp
        genreCache.setLastUpdate(Instant.now().minus(Duration.ofHours(5)));

        List<ImmutableGenre> updatedCachedGenres = genreCache.retrieveGenresFromCache();

        // Ensure that the cache has been updated
        Assertions.assertNotSame(initialCachedGenres, updatedCachedGenres);
    }
}
