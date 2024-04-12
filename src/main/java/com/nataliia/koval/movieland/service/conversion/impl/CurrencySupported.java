package com.nataliia.koval.movieland.service.conversion.impl;

import com.nataliia.koval.movieland.exception.ConvertCurrencyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum CurrencySupported {
    USD("USD"), EUR("EUR"), UAH("UAH");

    private final String name;

    public static String containsValue(String value) {
        if (Stream.of(CurrencySupported.values()).anyMatch(currency -> currency.getName().equalsIgnoreCase(value))) {
            return value;
        }
        throw new ConvertCurrencyException("Unsupported currency: " + value + ". Price can be converted to USD or EUR.");
    }
}
