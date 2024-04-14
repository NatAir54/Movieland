package com.nataliia.koval.movieland.exception;

public class InvalidSortingException extends RuntimeException {

    private static final String INVALID_SORTING_MESSAGE = "Invalid sorting order: %s. Sorting order should be 'asc' or 'desc'.";

    public InvalidSortingException(String message) {
        super(String.format(INVALID_SORTING_MESSAGE, message));
    }
}
