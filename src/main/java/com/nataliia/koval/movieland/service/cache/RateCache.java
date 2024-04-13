package com.nataliia.koval.movieland.service.cache;

import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;

/**
 * Interface representing a cache for currency conversion rates.
 */
public interface RateCache {

    /**
     * Fetches the exchange rate for the specified currency.
     *
     * @param currency The currency for which the exchange rate is to be fetched.
     * @return The exchange rate for the specified currency.
     */
    double fetchRate(CurrencySupported currency);
}
