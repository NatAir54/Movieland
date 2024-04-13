package com.nataliia.koval.movieland.service.conversion.impl;

import com.nataliia.koval.movieland.exception.ConvertCurrencyException;
import java.util.stream.Stream;

public enum CurrencySupported {
    UAH(), USD(), EUR();


    public static void validate(String value) {
        if (Stream.of(CurrencySupported.values()).noneMatch(currency -> currency.name().equals(value.toUpperCase()))) {
            throw new ConvertCurrencyException("Unsupported currency: " + value + ". Price can be converted to USD or EUR.");
        }
    }
}
