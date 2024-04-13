package com.nataliia.koval.movieland.service.cache;

import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;

public interface RateCache {
    double fetchRate(CurrencySupported currency);
}
