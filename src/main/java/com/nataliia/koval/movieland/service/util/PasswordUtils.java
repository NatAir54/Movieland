package com.nataliia.koval.movieland.service.util;

public interface PasswordUtils {
    boolean verifyPassword(String password, String storedPasswordHash);
}
