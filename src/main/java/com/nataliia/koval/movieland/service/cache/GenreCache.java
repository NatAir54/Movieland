package com.nataliia.koval.movieland.service.cache;

import java.util.List;

/**
 * Interface for accessing cached genre data.
 */
public interface GenreCache {

    /**
     * Retrieves genres from the cache.
     *
     * @return List of ImmutableGenre objects representing the genres retrieved from the cache.
     */
    List<ImmutableGenre> getAll();
}
