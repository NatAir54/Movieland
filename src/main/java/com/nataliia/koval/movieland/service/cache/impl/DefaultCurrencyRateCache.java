package com.nataliia.koval.movieland.service.cache.impl;

import com.nataliia.koval.movieland.exception.ConvertCurrencyException;
import com.nataliia.koval.movieland.service.cache.Cache;
import com.nataliia.koval.movieland.service.cache.CurrencyRateCache;
import com.nataliia.koval.movieland.service.conversion.impl.CurrencySupported;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Cache
public class DefaultCurrencyRateCache implements CurrencyRateCache {

    private static final String EXCHANGE_RATE_BASE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=";

    private final Map<CurrencySupported, Double> ratesCache = new ConcurrentHashMap<>();

    private final RestTemplate restTemplate =  new RestTemplate();


    @PostConstruct
    void initCache() {
        if(ratesCache.isEmpty()) {
            updateRates();
        }
    }

    @Scheduled(cron = "${cache.rates.update.schedule}")
    private void updateRates() {
        ratesCache.clear();
        for (CurrencySupported currency : CurrencySupported.values()) {
            double rate = 1.0;
            if (currency != CurrencySupported.UAH) {
                rate = fetchRateByCurrency(currency.name());
            }
            ratesCache.put(currency, rate);
        }
    }

    @Override
    public double getExchangeRate(CurrencySupported currency) {
        return ratesCache.getOrDefault(currency, 0.0);
    }

    private double fetchRateByCurrency(String currency) {
        String jsonResponse = restTemplate.getForObject(EXCHANGE_RATE_BASE_URL + currency + "&json", String.class);
        return parseExchangeRateFromJson(jsonResponse);
    }

    private double parseExchangeRateFromJson(String jsonResponse) {
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            return jsonObject.getDouble("rate");
        } catch (Exception e) {
            throw new ConvertCurrencyException("Error parsing exchange rate from JSON: " + e.getMessage(), e);
        }
    }
}
