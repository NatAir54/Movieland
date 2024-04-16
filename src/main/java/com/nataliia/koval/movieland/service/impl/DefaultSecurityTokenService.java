package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.service.SecurityTokenService;
import com.nataliia.koval.movieland.service.cache.TokenCache;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class DefaultSecurityTokenService implements SecurityTokenService {
    @Value("${token.expiration.time}")
    private long tokenExpirationTime;

    private final TokenCache<String, String> tokenCache;
    private SecretKey secretKey;


    @PostConstruct
    public void init() {
        this.secretKey = generateSecretKey();
    }

    @Override
    public String generateAndStoreTokenInCache(String email) {
        String token = generateToken(email);
        tokenCache.put(token, email);
        return token;
    }

    @Override
    public void invalidateToken(String token) {
        tokenCache.remove(token);
    }

    private String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(secretKey)
                .compact();
    }

    private SecretKey generateSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
