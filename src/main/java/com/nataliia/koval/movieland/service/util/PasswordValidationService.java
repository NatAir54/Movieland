package com.nataliia.koval.movieland.service.util;

public interface PasswordValidationService {
    boolean verifyPassword(String password, String storedPasswordHash);
}
