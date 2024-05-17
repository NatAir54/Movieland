package com.nataliia.koval.movieland.service;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtSecurityTokenService {

    String generateAndStoreTokenInCache(String email);

    String invalidateToken(String uuid);

    boolean isTokenInvalid(String token);

    String extractUsername(String token);
}
