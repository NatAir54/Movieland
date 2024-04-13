package com.nataliia.koval.movieland.service.conversion.impl;

import com.nataliia.koval.movieland.exception.ConvertCurrencyException;
import java.util.stream.Stream;

public enum CurrencySupported {
    UAH(), USD(), EUR();


    public static CurrencySupported validate(String value) {
        String valueValidated = value.toUpperCase();

        boolean isNotSupportedCurrency = Stream.of(CurrencySupported.values())
                .noneMatch(currency -> currency.name().equals(valueValidated));

        if (isNotSupportedCurrency) {
            throw new ConvertCurrencyException("Unsupported currency: " + value + ". Price can be converted to USD or EUR.");
        }
        return CurrencySupported.valueOf(valueValidated);
    }
}
