package com.nataliia.koval.movieland.service.conversion;

import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;

/**
 * Interface for converting movie prices between different currencies.
 */
public interface CurrencyConverter {

    /**
     * Converts the price of a movie from one currency to another.
     *
     * @param price    The price of the movie in the original currency.
     * @param currency The target currency to which the price should be converted.
     * @return The converted price of the movie in the target currency.
     */
    double convert(double price, CurrencySupported currency);
}
