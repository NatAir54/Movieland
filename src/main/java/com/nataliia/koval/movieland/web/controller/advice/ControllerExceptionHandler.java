package com.nataliia.koval.movieland.web.controller.advice;

import com.nataliia.koval.movieland.exception.GenreNotFoundException;
import com.nataliia.koval.movieland.exception.InvalidSortingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleGenreNotFoundException(GenreNotFoundException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.ok(Collections.singletonMap("error_message", errorMessage));
    }

    @ExceptionHandler(InvalidSortingException.class)
    public ResponseEntity<Map<String, String>> handleInvalidSortingException(InvalidSortingException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.ok(Collections.singletonMap("error_message", errorMessage));
    }
}