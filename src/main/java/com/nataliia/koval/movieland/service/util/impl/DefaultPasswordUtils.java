package com.nataliia.koval.movieland.service.util.impl;

import com.nataliia.koval.movieland.service.util.PasswordUtils;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class DefaultPasswordUtils implements PasswordUtils {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean verifyPassword(String password, String storedPasswordHash) {
        return passwordEncoder.matches(password, storedPasswordHash);
    }
}
