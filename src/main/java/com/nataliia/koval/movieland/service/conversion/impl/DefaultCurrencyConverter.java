package com.nataliia.koval.movieland.service.conversion.impl;

import com.nataliia.koval.movieland.service.cache.RateCache;
import com.nataliia.koval.movieland.service.conversion.Converter;
import com.nataliia.koval.movieland.service.conversion.CurrencyConverter;
import com.nataliia.koval.movieland.exception.ConvertCurrencyException;

import java.util.Locale;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Converter
public class DefaultCurrencyConverter implements CurrencyConverter {

    private final RateCache rateCache;


    @Override
    public double convert(double price, CurrencySupported currency) {
        double exchangeRate = rateCache.fetchRate(currency);

        if (exchangeRate <= 0) {
            throw new ConvertCurrencyException("Invalid exchange rate for currency: " + currency);
        }

        return roundToTwoDecimals(price / exchangeRate);
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
