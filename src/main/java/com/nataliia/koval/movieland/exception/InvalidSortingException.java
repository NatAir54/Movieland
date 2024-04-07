package com.nataliia.koval.movieland.exception;

public class InvalidSortingException extends RuntimeException {
    private static final String INVALID_SORTING_MESSAGE = "Invalid sorting order: %s.";

    public InvalidSortingException(String message) {
        super(String.format(INVALID_SORTING_MESSAGE, message));
    }
}
