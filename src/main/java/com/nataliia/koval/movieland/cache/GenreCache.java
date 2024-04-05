package com.nataliia.koval.movieland.cache;

import com.nataliia.koval.movieland.entity.Genre;

import java.time.Instant;
import java.util.List;

public interface GenreCache {

    List<ImmutableGenre> retrieveGenresFromCache();
    void setLastUpdate(Instant lastUpdate);
}
