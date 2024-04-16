package com.nataliia.koval.movieland.service;

public interface SecurityTokenService {

    String generateAndStoreTokenInCache(String email);

    void invalidateToken(String uuid);
}
