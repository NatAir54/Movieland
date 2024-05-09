package com.nataliia.koval.movieland.service.cache.impl;

import com.nataliia.koval.movieland.service.cache.Cache;
import com.nataliia.koval.movieland.service.cache.CurrencyRateCache;
import com.nataliia.koval.movieland.service.conversion.ExchangeRateClient;
import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Cache
public class DefaultCurrencyRateCache implements CurrencyRateCache {

    private final Map<CurrencySupported, Double> ratesCache = new ConcurrentHashMap<>();

    private final ExchangeRateClient exchangeRateClient;


    @PostConstruct
    void initCache() {
        updateRates();
    }

    @Override
    public double getExchangeRate(CurrencySupported currency) {
        return ratesCache.get(currency);
    }

    @Scheduled(cron = "${cache.rates.update.schedule}")
    void updateRates() {
        String[] currencyNames = CurrencySupported.getAllCurrencyNamesExceptUah();
        Map<String, Double> exchangeRates = exchangeRateClient.fetchRatesForCurrencies(currencyNames);
        for (String currencyName : currencyNames) {
            CurrencySupported currency = CurrencySupported.valueOf(currencyName);
            double rate = exchangeRates.get(currencyName);
            ratesCache.put(currency, rate);
        }
    }
}
