package com.nataliia.koval.movieland.web.controller.advice;

import com.nataliia.koval.movieland.exception.GenreNotFoundException;
import com.nataliia.koval.movieland.exception.InvalidSortingException;
import com.nataliia.koval.movieland.exception.MovieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleGenreNotFoundException(GenreNotFoundException ex) {
        String errorMessage = ex.getMessage();
        Map<String, String> errors = Map.of("error_message", errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(InvalidSortingException.class)
    public ResponseEntity<Map<String, String>> handleInvalidSortingException(InvalidSortingException ex) {
        String errorMessage = ex.getMessage();
        Map<String, String> errors = Map.of("error_message", errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleMovieNotFoundException(MovieNotFoundException ex) {
        String errorMessage = ex.getMessage();
        Map<String, String> errors = Map.of("error_message", errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }
}