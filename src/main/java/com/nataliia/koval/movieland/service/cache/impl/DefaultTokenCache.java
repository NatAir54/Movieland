package com.nataliia.koval.movieland.service.cache.impl;

import com.nataliia.koval.movieland.service.cache.Cache;
import com.nataliia.koval.movieland.service.cache.TokenCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Cache
public class DefaultTokenCache implements TokenCache<String, String> {
    private final Map<String, TokenEntry> tokenMap = new ConcurrentHashMap<>();

    @Value("${token.expiration.time}")
    private long tokenExpirationTime;


    public void put(String token, String email) {
        tokenMap.put(token, new TokenEntry(email));
    }

    public void remove(String token) {
        if (tokenMap.containsKey(token)) {
            tokenMap.remove(token);
        } else {
            log.debug("Token {} not found in cache during logout process.", token);
        }
    }

    @Scheduled(fixedDelayString = "${token.expiration.check.interval}")
    public void removeExpiredTokens() {
        if (!tokenMap.isEmpty()) {
            long currentTime = System.currentTimeMillis();
            tokenMap.entrySet().removeIf(entry -> currentTime - entry.getValue().creationTime > tokenExpirationTime);
        }
    }


    private static class TokenEntry {
        private final String email;
        private final long creationTime;

        public TokenEntry(String email) {
            this.email = email;
            this.creationTime = System.currentTimeMillis();
        }
    }
}
