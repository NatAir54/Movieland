package com.nataliia.koval.movieland.web.controller.advice;

import com.nataliia.koval.movieland.exception.ConvertCurrencyException;
import com.nataliia.koval.movieland.exception.GenreNotFoundException;
import com.nataliia.koval.movieland.exception.InvalidSortingException;
import com.nataliia.koval.movieland.exception.MovieNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Objects;

class ControllerExceptionHandlerTest {
    @Test
    @DisplayName("Test handling GenreNotFoundException with non-existing genre ID")
    void handleGenreNotFoundExceptionWithNonExistingId() {
        int genreId = 1000;
        GenreNotFoundException ex = new GenreNotFoundException(genreId);
        ControllerExceptionHandler handler = new ControllerExceptionHandler();

        ResponseEntity<Map<String, String>> response = handler.handleGenreNotFoundException(ex);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(response.getBody()).containsKey("error_message"));
        Assertions.assertEquals("Genre with specified id 1000 not found. Check the request details.", response.getBody().get("error_message"));
    }

    @Test
    @DisplayName("Test handling GenreNotFoundException with invalid genre ID")
    void handleGenreNotFoundExceptionWithInvalidId() {
        int invalidGenreId = -1;
        GenreNotFoundException ex = new GenreNotFoundException(invalidGenreId);
        ControllerExceptionHandler handler = new ControllerExceptionHandler();

        ResponseEntity<Map<String, String>> response = handler.handleGenreNotFoundException(ex);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(response.getBody()).containsKey("error_message"));
        Assertions.assertEquals("Invalid genre ID: -1. Genre ID should be a positive number.", response.getBody().get("error_message"));
    }

    @Test
    @DisplayName("Test handling InvalidSortingException")
    void handleInvalidSortingException() {
        InvalidSortingException ex = new InvalidSortingException("Invalid sorting order");
        ControllerExceptionHandler handler = new ControllerExceptionHandler();

        ResponseEntity<Map<String, String>> response = handler.handleInvalidSortingException(ex);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(response.getBody()).containsKey("error_message"));
        Assertions.assertEquals("Invalid sorting order: Invalid sorting order. Sorting order should be 'asc' or 'desc'.", response.getBody().get("error_message"));
    }

    @Test
    @DisplayName("Test handling MovieNotFoundException with non-existing genre ID")
    void handleMovieNotFoundExceptionWithNonExistingId() {
        int movieId = 1000;
        MovieNotFoundException ex = new MovieNotFoundException(movieId);
        ControllerExceptionHandler handler = new ControllerExceptionHandler();

        ResponseEntity<Map<String, String>> response = handler.handleMovieNotFoundException(ex);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(response.getBody()).containsKey("error_message"));
        Assertions.assertEquals("Movie with specified id 1000 not found. Check the request details.", response.getBody().get("error_message"));
    }

    @Test
    @DisplayName("Test handling MovieNotFoundException with invalid movie ID")
    void handleMovieNotFoundExceptionWithWithInvalidId() {
        int invalidMovieId = -1;
        MovieNotFoundException ex = new MovieNotFoundException(invalidMovieId);
        ControllerExceptionHandler handler = new ControllerExceptionHandler();

        ResponseEntity<Map<String, String>> response = handler.handleMovieNotFoundException(ex);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(response.getBody()).containsKey("error_message"));
        Assertions.assertEquals("Invalid movie ID: -1. Movie ID should be a positive number.", response.getBody().get("error_message"));
    }

    @Test
    @DisplayName("Test handling ConvertCurrencyException")
    void handleConvertCurrencyException() {
        ConvertCurrencyException ex = new ConvertCurrencyException("Unsupported currency: abc");
        ControllerExceptionHandler handler = new ControllerExceptionHandler();

        ResponseEntity<Map<String, String>> response = handler.handleConvertCurrencyException(ex);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(response.getBody()).containsKey("error_message"));
        Assertions.assertEquals("Unsupported currency: abc", response.getBody().get("error_message"));
    }
}