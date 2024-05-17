package com.nataliia.koval.movieland.service.security;

import com.nataliia.koval.movieland.entity.Token;

public interface TokenStorageService {
    Token save(Token token);

    String delete(String tokenValue);

    boolean exists(String tokenValue);
}
