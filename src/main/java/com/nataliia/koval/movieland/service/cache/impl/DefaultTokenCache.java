package com.nataliia.koval.movieland.service.cache.impl;

import com.nataliia.koval.movieland.service.cache.Cache;
import com.nataliia.koval.movieland.service.cache.TokenCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Cache
public class DefaultTokenCache implements TokenCache<String, String> {
    private final Map<String, String> tokenMap = new ConcurrentHashMap<>();

    public void put(String token, String email) {
        tokenMap.put(token, email);
    }

    public void remove(String token) {
        tokenMap.remove(token);
    }

    public boolean isTokenInvalid(String token) {
        return !tokenMap.containsKey(token);
    }
}
