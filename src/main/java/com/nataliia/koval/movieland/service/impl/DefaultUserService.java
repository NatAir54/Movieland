package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.entity.User;
import com.nataliia.koval.movieland.repository.UserRepository;
import com.nataliia.koval.movieland.service.UserService;
import com.nataliia.koval.movieland.service.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;


    @Override
    public String getUserNickname(String email) {
        return userRepository.findByEmail(email)
                .map(User::getNickname)
                .orElse(null);
    }

    @Override
    public boolean isInvalidUser(String email, String password) {
        String storedPasswordHash = getUserPasswordHashByEmail(email);

        if (storedPasswordHash != null) {
            boolean passwordMatches = passwordUtils.verifyPassword(password, storedPasswordHash);
            return !passwordMatches;
        }
        return false;
    }

    private String getUserPasswordHashByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getPasswordHash)
                .orElse(null);
    }
}
