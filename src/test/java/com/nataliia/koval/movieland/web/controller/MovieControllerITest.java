package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.dto.MovieDto;
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
import org.springframework.web.client.HttpClientErrorException;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieControllerITest {
    private static final String URL = "/api/v1/movies";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Integration test for GET /api/v1/movies")
    void findAll_shouldReturnStatusOkAndContentTypeApplicationJson() {
        ResponseEntity<List<MovieDto>> responseEntity = testRestTemplate.exchange(
                URL,
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
        ResponseEntity<List<MovieDto>> responseEntity = testRestTemplate.exchange(
                URL + "/random",
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
        String genreId = "1";

        ResponseEntity<List<MovieDto>> responseEntity = testRestTemplate.exchange(
                URL + "/genre/{genreId}",
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
        Assertions.assertEquals(16, movies.size());
    }

    @Test
    @DisplayName("Integration test for handling GenreNotFoundException with non-existing genre ID")
    void handleGenreNotFoundException_withNonExistingGenreId() {
        String nonExistingGenreId = "1000";

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                URL + "/genre/{genreId}",
                String.class,
                nonExistingGenreId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).contains("Genre with specified id " + nonExistingGenreId + " not found"));
    }

    @Test
    @DisplayName("Integration test for handling GenreNotFoundException with negative genre ID")
    void handleGenreNotFoundException_withInvalidGenreId() {
         String genreId = "-1";

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                URL + "/genre/{genreId}",
                String.class,
                genreId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).contains("Invalid genre ID: " + genreId));
    }

    @Test
    @DisplayName("Integration test for handling GenreNotFoundException with non-integer genre ID")
    void handleGenreNotFoundException_withNonIntegerGenreId() {
        String genreId = "abc";

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                URL + "/genre/{genreId}",
                String.class,
                genreId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).contains("Invalid genre ID: " + genreId));
    }

    @Test
    @DisplayName("Integration test for GET /api/v1/movies sorted by rating in ascending order")
    void findAllSortedByRatingAsc_shouldReturnSortedMoviesByRatingAsc() throws IOException {
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(
                URL + "?ratingOrder=asc",
                HttpMethod.GET,
                null,
                String.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        ObjectMapper objectMapper = new ObjectMapper();
        List<MovieDto> movies = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<MovieDto>>() {});
        Assertions.assertNotNull(movies);
        Assertions.assertFalse(movies.isEmpty());

        double previousRating = Double.MIN_VALUE;
        for (MovieDto movie : movies) {
            Assertions.assertTrue(movie.getRating() >= previousRating);
            previousRating = movie.getRating();
        }
    }

    @Test
    @DisplayName("Integration test for GET /api/v1/movies sorted by rating in descending order")
    void findAllSortedByRatingDesc_shouldReturnSortedMoviesByRatingDesc() throws IOException {
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(
                URL + "?ratingOrder=desc",
                HttpMethod.GET,
                null,
                String.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        ObjectMapper objectMapper = new ObjectMapper();
        List<MovieDto> movies = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<MovieDto>>() {});
        Assertions.assertNotNull(movies);
        Assertions.assertFalse(movies.isEmpty());

        double previousRating = Double.MAX_VALUE;
        for (MovieDto movie : movies) {
            Assertions.assertTrue(movie.getRating() <= previousRating);
            previousRating = movie.getRating();
        }
    }

    @Test
    @DisplayName("Integration test for GET /api/v1/movies sorted by price in descending order")
    void findAllSortedByPriceDesc_shouldReturnSortedMoviesByPriceDesc() throws IOException {
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(
                URL + "?priceOrder=desc",
                HttpMethod.GET,
                null,
                String.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        ObjectMapper objectMapper = new ObjectMapper();
        List<MovieDto> movies = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<MovieDto>>() {});
        Assertions.assertNotNull(movies);
        Assertions.assertFalse(movies.isEmpty());

        double previousPrice = Double.MAX_VALUE;
        for (MovieDto movie : movies) {
            Assertions.assertTrue(movie.getPrice() <= previousPrice);
            previousPrice = movie.getPrice();
        }
    }

    @Test
    @DisplayName("Integration test for GET /api/v1/movies sorted by price in ascending order")
    void findAllSortedByPriceAsc_shouldReturnSortedMoviesByPriceAsc() throws IOException {
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(
                URL + "?priceOrder=asc",
                HttpMethod.GET,
                null,
                String.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getContentType())
                .isCompatibleWith(MediaType.APPLICATION_JSON));

        ObjectMapper objectMapper = new ObjectMapper();
        List<MovieDto> movies = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<MovieDto>>() {});
        Assertions.assertNotNull(movies);
        Assertions.assertFalse(movies.isEmpty());

        double previousPrice = Double.MIN_VALUE;
        for (MovieDto movie : movies) {
            Assertions.assertTrue(movie.getPrice() >= previousPrice);
            previousPrice = movie.getPrice();
        }
    }

    @Test
    @DisplayName("Integration test for handling InvalidSortingException with invalid rating sorting order")
    void handleInvalidSortingException_withInvalidRatingSortingOrder() {
        try {
            testRestTemplate.getForEntity(
                    URL + "?ratingOrder=invalid",
                    String.class);
        } catch (HttpClientErrorException e) {
            Assertions.assertEquals(HttpStatus.OK, e.getStatusCode());
            Assertions.assertTrue(e.getResponseBodyAsString().contains("Invalid sorting order: invalid"));
        }
    }

    @Test
    @DisplayName("Integration test for handling InvalidSortingException with invalid sorting order")
    void handleInvalidSortingException_withInvalidPriceSortingOrder() {
        try {
            testRestTemplate.getForEntity(
                    URL + "?priceOrder=invalid",
                    String.class);
        } catch (HttpClientErrorException e) {
            Assertions.assertEquals(HttpStatus.OK, e.getStatusCode());
            Assertions.assertTrue(e.getResponseBodyAsString().contains("Invalid sorting order: invalid"));
        }
    }

}