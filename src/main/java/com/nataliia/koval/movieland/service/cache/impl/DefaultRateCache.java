package com.nataliia.koval.movieland.service.cache.impl;

import com.nataliia.koval.movieland.exception.ConvertCurrencyException;
import com.nataliia.koval.movieland.repository.RateRepository;
import com.nataliia.koval.movieland.service.cache.Cache;
import com.nataliia.koval.movieland.service.cache.RateCache;
import com.nataliia.koval.movieland.entity.Rate;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
@Cache
public class DefaultRateCache implements RateCache {

    private final CopyOnWriteArrayList<Rate> cache = new CopyOnWriteArrayList<>();

    private final RateRepository rateRepository;

    @PostConstruct
    void initCache() {
        if(cache.isEmpty()) {
            cache.addAll(fetchRatesFromDatabase());
        }
    }

    @Override
    public double fetchRate(String currency) {
        return cache.stream()
                .filter(rate -> rate.getName().equalsIgnoreCase(currency))
                .mapToDouble(Rate::getValue)
                .findFirst()
                .orElse(0.0);
    }

    @Scheduled(cron = "${cache.rates_interval}")
    private void updateRates() {
        List<Rate> rates = rateRepository.findAll();
        rates.forEach(rate -> {
            rate.setValue(fetchRateByCurrency(rate.getName()));
            rate.setLastUpdated(LocalDateTime.now());
            rateRepository.save(rate);
        });
        cache.clear();
        cache.addAll(rates);
    }

    private double fetchRateByCurrency(String currency) {
        String EXCHANGE_RATE_BASE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=";
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(EXCHANGE_RATE_BASE_URL + currency.toUpperCase() + "&json"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return parseExchangeRateFromJson(response.body());
        } catch (IOException | InterruptedException e) {
            throw new ConvertCurrencyException("Failed to fetch exchange rate for currency: " + currency, e);
        }
    }

    private double parseExchangeRateFromJson(String jsonResponse) {
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            if (!jsonArray.isEmpty()) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                return jsonObject.getDouble("rate");
            } else {
                throw new ConvertCurrencyException("No exchange rate data found in JSON response.");
            }
        } catch (JSONException e) {
            throw new ConvertCurrencyException("Error parsing exchange rate from JSON: " + e.getMessage(), e);
        }
    }

    private List<Rate> fetchRatesFromDatabase() {
        return new ArrayList<>(rateRepository.findAll());
    }
}
