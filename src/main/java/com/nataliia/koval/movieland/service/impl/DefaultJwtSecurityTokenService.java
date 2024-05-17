package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.entity.Token;
import com.nataliia.koval.movieland.repository.impl.DefaultTokenRepository;
import com.nataliia.koval.movieland.service.JwtSecurityTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class DefaultJwtSecurityTokenService implements JwtSecurityTokenService {
    private final DefaultTokenRepository defaultTokenRepository;
    private SecretKey secretKey;

    @Value("${token.expiration.time}")
    private long tokenExpirationTime;


    @PostConstruct
    public void init() {
        secretKey = generateSecretKey();
    }

    @Override
    public String generateAndStoreTokenInCache(String email) {
        String token = generateToken(email);
        defaultTokenRepository.save(new Token(token, email));
        return token;
    }

    @Override
    public String invalidateToken(String token) {
        return defaultTokenRepository.delete(token);
    }

    @Override
    public boolean isTokenInvalid(String token) {
        if (defaultTokenRepository.exists(token)) {
            try {
                Claims claims = parseToken(token);
                return claims.getExpiration().before(new Date());
            } catch (JwtException | IllegalArgumentException e) {
                return true;
            }
        }
        return true;
    }

    @Override
    public String extractUsername(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    private Claims parseToken(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        return parser.parseClaimsJws(token).getBody();
    }

    private String generateToken(String email) {
        Date expirationDate = new Date(System.currentTimeMillis() + tokenExpirationTime);
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    private SecretKey generateSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
