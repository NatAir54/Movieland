package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.service.SecurityTokenService;
import com.nataliia.koval.movieland.service.cache.TokenCache;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class DefaultSecurityTokenService implements SecurityTokenService {
    private static final long TOKEN_EXPIRATION_TIME = 2 * 60 * 60 * 1000; // 2 hours in milliseconds
    private final TokenCache<String, String> tokenCache;
    private SecretKey secretKey;


    @PostConstruct
    public void init() {
        this.secretKey = generateSecretKey();
    }

    @Override
    public String generateAndStoreTokenInCache(String email) {
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();

        tokenCache.put(token, email);
        return token;
    }

    @Override
    public boolean isTokenInvalid(String token) {
        return tokenCache.isTokenInvalid(token);
    }

    @Override
    public void invalidateToken(String token) {
        tokenCache.remove(token);
    }

    private SecretKey generateSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
