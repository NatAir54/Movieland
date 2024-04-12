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
    public double convert(double price, String currency) {
        String currencyChecked = CurrencySupported.containsValue(currency);

        double exchangeRate = rateCache.fetchRate(currencyChecked);

        if (exchangeRate <= 0) {
            throw new ConvertCurrencyException("Invalid exchange rate for currency: " + currency);
        }

        double convertedPrice = price / exchangeRate;

        String formattedPrice = String.format(Locale.US, "%.2f", convertedPrice);

        return Double.parseDouble(formattedPrice);
    }
}
