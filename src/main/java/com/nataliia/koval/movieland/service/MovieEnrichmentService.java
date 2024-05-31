package com.nataliia.koval.movieland.service;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;

public interface MovieEnrichmentService {
    MovieDto enrichMovie(Movie movie, CurrencySupported currency);
}
