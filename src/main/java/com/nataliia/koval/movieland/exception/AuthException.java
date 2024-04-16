package com.nataliia.koval.movieland.exception;


public class AuthException extends RuntimeException {

    private static final String MESSAGE = "%s";

    public AuthException(String message) {
        super(String.format(MESSAGE, message));
    }
}
