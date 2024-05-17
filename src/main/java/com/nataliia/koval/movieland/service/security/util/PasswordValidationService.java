package com.nataliia.koval.movieland.service.security.util;

public interface PasswordValidationService {
    boolean verifyPassword(String password, String storedPasswordHash);
}
