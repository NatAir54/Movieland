package com.nataliia.koval.movieland.service.security.impl;

import com.nataliia.koval.movieland.entity.Token;
import com.nataliia.koval.movieland.service.security.TokenStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultTokenStorageService implements TokenStorageService {
    public static final String HASH_KEY = "Token";
    private final RedisTemplate<String, Object> template;


    @Override
    public Token save(Token token) {
        template.opsForHash().put(HASH_KEY, token.getTokenValue(), token);
        return token;
    }

    @Override
    public String delete(String tokenValue) {
        template.opsForHash().delete(HASH_KEY, tokenValue);
        return "Logout succeed.";
    }

    @Override
    public boolean exists(String tokenValue) {
        return template.opsForHash().hasKey(HASH_KEY, tokenValue);
    }
}
