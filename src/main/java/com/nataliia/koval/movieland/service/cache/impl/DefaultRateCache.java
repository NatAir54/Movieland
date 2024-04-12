package com.nataliia.koval.movieland.service.cache.impl;

import com.nataliia.koval.movieland.exception.ConvertCurrencyException;
import com.nataliia.koval.movieland.repository.RateRepository;
import com.nataliia.koval.movieland.service.cache.Cache;
import com.nataliia.koval.movieland.service.cache.RateCache;
import com.nataliia.koval.movieland.entity.Rate;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@RequiredArgsConstructor
@Cache
public class DefaultRateCache implements RateCache {

    private static final String EXCHANGE_RATE_BASE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=";

    private final CopyOnWriteArrayList<Rate> ratesCache = new CopyOnWriteArrayList<>();

    private final RateRepository rateRepository;


    @PostConstruct
    void initCache() {
        if (ratesCache.isEmpty()) {
            ratesCache.addAll(fetchRatesFromDatabase());
        }
    }

    @Override
    public double fetchRate(String currency) {
        return ratesCache.stream()
                .filter(rate -> rate.getName().equalsIgnoreCase(currency))
                .mapToDouble(Rate::getValue)
                .findFirst()
                .orElse(0.0);
    }

    @Scheduled(cron = "${cache.rates.interval}")
    private void updateRates() {
        List<Rate> rates = rateRepository.findAll();
        rates.forEach(rate -> {
            rate.setValue(fetchRateByCurrency(rate.getName()));
            rate.setLastUpdated(LocalDateTime.now());
            rateRepository.save(rate);
        });
        ratesCache.clear();
        ratesCache.addAll(rates);
    }

    private double fetchRateByCurrency(String currency) {
        WebClient webClient = WebClient.create(EXCHANGE_RATE_BASE_URL);

        try {
            String jsonResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path(currency.toUpperCase()).queryParam("json").build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return parseExchangeRateFromJson(jsonResponse);
        } catch (Exception e) {
            throw new ConvertCurrencyException("Failed to fetch exchange rate for currency: " + currency, e);
        }
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

    private List<Rate> fetchRatesFromDatabase() {
        return rateRepository.findAll();
    }
}
