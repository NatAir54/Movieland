package com.nataliia.koval.movieland.service;

public interface UserService {
    String getUserNickname(String email);

    boolean isInvalidUser(String email, String password);
}
