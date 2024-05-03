package com.nataliia.koval.movieland.service;

public interface JwtSecurityTokenService {

    String generateAndStoreTokenInCache(String email);

    String invalidateToken(String uuid);

    boolean isTokenInvalid(String token);
}
