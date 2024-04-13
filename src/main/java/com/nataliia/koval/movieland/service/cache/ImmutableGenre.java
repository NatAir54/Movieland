package com.nataliia.koval.movieland.service.cache;

/**
 * Interface representing an immutable genre entity.
 */
public interface ImmutableGenre {

    /**
     * Retrieves the ID of the genre.
     *
     * @return The ID of the genre.
     */
    int getId();

    /**
     * Retrieves the name of the genre.
     *
     * @return The name of the genre.
     */
    String getName();
}
