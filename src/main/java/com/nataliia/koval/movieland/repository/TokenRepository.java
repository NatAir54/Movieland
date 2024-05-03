package com.nataliia.koval.movieland.repository;

import com.nataliia.koval.movieland.entity.Token;

public interface TokenRepository {
    Token save(Token token);

    String delete(String tokenValue);

    boolean exists(String tokenValue);
}
