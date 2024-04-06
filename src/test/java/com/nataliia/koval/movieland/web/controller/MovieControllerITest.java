package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.dto.MovieDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
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

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        List<MovieDto> movies = responseEntity.getBody();
        Assertions.assertNotNull(movies);
        Assertions.assertFalse(movies.isEmpty());
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

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        List<MovieDto> movies = responseEntity.getBody();
        Assertions.assertNotNull(movies);
        Assertions.assertEquals(3, movies.size());
    }

    @Test
    @DisplayName("Integration test for GET /api/v1/movies/genre/{genreId}")
    void findByGenre_shouldReturnStatusOkAndContentTypeApplicationJson() {
        log.info("Running findByGenre_shouldReturnStatusOkAndContentTypeApplicationJson test");

        int genreId = 1;

        ResponseEntity<List<MovieDto>> responseEntity = testRestTemplate.exchange(
                "/api/v1/movies/genre/{genreId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                genreId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        List<MovieDto> movies = responseEntity.getBody();
        Assertions.assertNotNull(movies);
        Assertions.assertEquals(9, movies.size());
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

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        List<MovieDto> movies = responseEntity.getBody();
        Assertions.assertNotNull(movies);
        Assertions.assertTrue(movies.isEmpty());
    }
}