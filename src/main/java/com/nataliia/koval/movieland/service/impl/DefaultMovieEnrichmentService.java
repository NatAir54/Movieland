package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.mapper.MovieMapper;
import com.nataliia.koval.movieland.service.MovieEnrichmentService;
import com.nataliia.koval.movieland.service.conversion.CurrencyConverter;
import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Deprecated
@RequiredArgsConstructor
@Service
public class DefaultMovieEnrichmentService implements MovieEnrichmentService {
    private final CurrencyConverter currencyConverter;

    private final MovieMapper movieMapper;

    @Override
    public MovieDto enrichMovie(Movie movie, CurrencySupported currency) {
        MovieDto movieDto = movieMapper.toDto(movie);

        if (currency != CurrencySupported.UAH) {
            double convertedPrice = currencyConverter.convert(movieDto.getPrice(), currency);
            double priceRoundedToTwoDecimals = new BigDecimal(convertedPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
            movieDto.setPrice(priceRoundedToTwoDecimals);
        }

        return movieDto;
    }
}
