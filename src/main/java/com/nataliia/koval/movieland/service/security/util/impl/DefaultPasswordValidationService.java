package com.nataliia.koval.movieland.service.security.util.impl;

import com.nataliia.koval.movieland.service.security.util.PasswordValidationService;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class DefaultPasswordValidationService implements PasswordValidationService {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean verifyPassword(String password, String storedPasswordHash) {
        return passwordEncoder.matches(password, storedPasswordHash);
    }
}
