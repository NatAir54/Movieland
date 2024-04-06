package com.nataliia.koval.movieland.cache;

import java.util.List;

public interface GenreCache {
    List<ImmutableGenre> retrieveGenresFromCache();
}
