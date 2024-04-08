package com.nataliia.koval.movieland.exception;

public class GenreNotFoundException extends RuntimeException {
    private static final String NOT_FOUND_ID_MESSAGE = "Genre with specified id %d not found. Check the request details.";
    private static final String INVALID_ID_MESSAGE = "Invalid genre ID: %d. Genre ID should be a positive number.";
    private static final String NOT_PARSED_ID_MESSAGE = "Invalid genre ID: %s. Genre ID should be a positive number.";

    public GenreNotFoundException(int genreId) {
        super(String.format(genreId <= 0 ? INVALID_ID_MESSAGE : NOT_FOUND_ID_MESSAGE, genreId));
    }

    public GenreNotFoundException(String genreId) {
        super(String.format(NOT_PARSED_ID_MESSAGE, genreId));
    }
}
