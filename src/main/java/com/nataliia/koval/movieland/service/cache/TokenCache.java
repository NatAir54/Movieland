package com.nataliia.koval.movieland.service.cache;

public interface TokenCache<K, V> {
    void put(K key, V value);
    void remove(K key);
    boolean isTokenInvalid(K key);
}
