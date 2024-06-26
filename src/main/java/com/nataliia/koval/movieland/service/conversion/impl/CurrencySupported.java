package com.nataliia.koval.movieland.service.conversion.impl;

import com.nataliia.koval.movieland.exception.ConvertCurrencyException;
import java.util.stream.Stream;

public enum CurrencySupported {
    UAH, USD, EUR;

    public static CurrencySupported parseCurrency(String value) {
        if (isNotSupportedCurrency(value)) {
            throw new ConvertCurrencyException("Unsupported currency: " + value + ". Price can be converted to USD or EUR.");
        }
        return CurrencySupported.valueOf(value);
    }

    public static String[] getAllCurrencyNamesExceptUah() {
        return Stream.of(values())
                .map(Enum::name)
                .filter(name -> !name.equals("UAH"))
                .toArray(String[]::new);
    }

    private static boolean isNotSupportedCurrency(String value) {
        return Stream.of(values())
                .noneMatch(currency -> currency.name().equals(value));
    }
}
