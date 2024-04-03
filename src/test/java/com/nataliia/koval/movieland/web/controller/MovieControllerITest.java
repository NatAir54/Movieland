package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.dto.MovieDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieControllerITest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Integration test for GET /api/v1/movies")
    void findAll_shouldReturnStatusOkAndContentTypeApplicationJson() {
        log.info("Running findAll_shouldReturnStatusOkAndContentTypeApplicationJson test");

        ResponseEntity<List<MovieDto>> responseEntity = testRestTemplate.exchange(
                "/api/v1/movies",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        List<MovieDto> movies = responseEntity.getBody();
        assertNotNull(movies);
        assertTrue(movies.size() > 0);
    }

    @Test
    @DisplayName("Integration test for GET /api/v1/movies/random")
    void findThreeRandom_shouldReturnStatusOkAndContentTypeApplicationJson() {
        log.info("Running findRandom_shouldReturnStatusOkAndContentTypeApplicationJson test");

        ResponseEntity<List<MovieDto>> responseEntity = testRestTemplate.exchange(
                "/api/v1/movies/random?randomMoviesNumber=3",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        List<MovieDto> movies = responseEntity.getBody();
        assertNotNull(movies);
        assertEquals(3, movies.size());
    }

    @Test
    @DisplayName("Integration test for GET /api/v1/movies/genre/{genreId}")
    void findByGenre_shouldReturnStatusOkAndContentTypeApplicationJson() {
        log.info("Running findByGenre_shouldReturnStatusOkAndContentTypeApplicationJson test");

        int genreId = 8;

        ResponseEntity<List<MovieDto>> responseEntity = testRestTemplate.exchange(
                "/api/v1/movies/genre/{genreId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                genreId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        List<MovieDto> movies = responseEntity.getBody();
        assertNotNull(movies);
        assertEquals(3, movies.size());
    }

    @Test
    @DisplayName("Integration test for GET /api/v1/movies/genre/{genreId} with invalid genre ID")
    void findByInvalidGenreId_shouldReturnEmptyList() {
        log.info("Running findByInvalidGenreId_shouldReturnEmptyList test");

        int invalidGenreId = -1;

        ResponseEntity<List<MovieDto>> responseEntity = testRestTemplate.exchange(
                "/api/v1/movies/genre/{genreId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                invalidGenreId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        List<MovieDto> movies = responseEntity.getBody();
        assertNotNull(movies);
        assertTrue(movies.isEmpty());
    }
}