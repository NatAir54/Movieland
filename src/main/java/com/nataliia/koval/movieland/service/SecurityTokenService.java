package com.nataliia.koval.movieland.service;

public interface SecurityTokenService {

    String generateAndStoreTokenInCache(String email);

    boolean isTokenInvalid(String token);

    void invalidateToken(String uuid);
}
