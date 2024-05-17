package com.nataliia.koval.movieland.service;

import com.nataliia.koval.movieland.entity.User;

public interface UserService {
    String getUserNickname(String email);

    boolean isInvalidUser(String email, String password);

    User findByEmail(String userEmail);
}
