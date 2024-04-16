package com.nataliia.koval.movieland.service.conversion.impl;

import com.nataliia.koval.movieland.service.cache.CurrencyRateCache;
import com.nataliia.koval.movieland.service.conversion.CurrencyConverter;
import com.nataliia.koval.movieland.exception.ConvertCurrencyException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class DefaultCurrencyConverter implements CurrencyConverter {

    private final CurrencyRateCache rateCache;


    @Override
    public double convert(double price, CurrencySupported currency) {
        double exchangeRate = rateCache.getExchangeRate(currency);
        if (isInvalidExchangeRate(exchangeRate)) {
            throw new ConvertCurrencyException("Invalid exchange rate for currency: " + currency);
        }
        return roundToTwoDecimals(price / exchangeRate);
    }

    private boolean isInvalidExchangeRate(double exchangeRate) {
        return exchangeRate <= 0;
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
