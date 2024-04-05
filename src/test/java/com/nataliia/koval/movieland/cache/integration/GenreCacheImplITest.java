package com.nataliia.koval.movieland.cache.integration;

import com.nataliia.koval.movieland.cache.ImmutableGenre;
import com.nataliia.koval.movieland.cache.impl.GenreCacheImpl;
import com.nataliia.koval.movieland.entity.Genre;
import com.nataliia.koval.movieland.repository.GenreRepository;
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
    @DisplayName("retrieveGenresFromCache_ShouldReturnGenresFromExistingDatabase")
    void retrieveGenresFromCache_ShouldReturnGenresFromExistingDatabase() {
        List<ImmutableGenre> cachedGenres = genreCache.retrieveGenresFromCache();

        Assertions.assertEquals(15, cachedGenres.size());
        Assertions.assertEquals("драма", cachedGenres.get(0).getName());
        Assertions.assertEquals("криминал", cachedGenres.get(1).getName());
    }

}
