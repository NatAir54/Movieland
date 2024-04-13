package com.nataliia.koval.movieland.service.conversion;

import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;

public interface CurrencyConverter {
    double convert(double price, CurrencySupported currency);
}
