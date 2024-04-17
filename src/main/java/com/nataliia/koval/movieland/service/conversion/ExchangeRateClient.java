package com.nataliia.koval.movieland.service.conversion;

import java.util.Map;

public interface ExchangeRateClient {
    Map<String, Double> fetchRatesForCurrencies(String[] currencies);
}
