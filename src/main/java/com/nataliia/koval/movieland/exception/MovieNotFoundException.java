package com.nataliia.koval.movieland.exception;

public class MovieNotFoundException extends RuntimeException {
    private static final String NOT_FOUND_ID_MESSAGE = "Movie with specified id %d not found. Check the request details.";
    private static final String INVALID_ID_MESSAGE = "Invalid movie ID: %d. Movie ID should be a positive number.";

    public MovieNotFoundException(int movieId) {
        super(String.format(movieId <= 0 ? INVALID_ID_MESSAGE : NOT_FOUND_ID_MESSAGE, movieId));
    }
}
