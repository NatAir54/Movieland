package com.nataliia.koval.movieland.service.conversion.impl;

import com.nataliia.koval.movieland.exception.ConvertCurrencyException;
import com.nataliia.koval.movieland.service.conversion.ExchangeRateClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
public class DefaultExchangeRateClient implements ExchangeRateClient {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Value("${nbu.exchange.rate.url}")
    private String exchangeRateBaseUrl;

    private final RestClient restClient = RestClient.create();


    public Map<String, Double> fetchRatesForCurrencies(String[] currencies) {
        String todayRates = fetchExchangeRatesJsonResponse();
        JSONArray jsonArray = new JSONArray(todayRates);
        return parseExchangeRatesFromJson(jsonArray, currencies);
    }

    @Transactional
    String fetchExchangeRatesJsonResponse() {
        String formattedDate = LocalDate.now().format(formatter);

        URI uri = UriComponentsBuilder.fromHttpUrl(exchangeRateBaseUrl)
                .queryParam("exchangedate", formattedDate)
                .queryParam("json", "")
                .build()
                .toUri();

        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(String.class);
    }

    private Map<String, Double> parseExchangeRatesFromJson(JSONArray jsonArray, String[] currencies) {
        Map<String, Double> ratesMap = new HashMap<>();
        try {
            Arrays.stream(currencies)
                    .forEach(currency -> findRateForCurrency(jsonArray, currency)
                            .ifPresent(jsonObject -> ratesMap.put(currency, jsonObject.getDouble("rate"))));
            return ratesMap;
        } catch (Exception e) {
            throw new ConvertCurrencyException("Error parsing exchange rate from JSON: " + e.getMessage());
        }
    }

    private Optional<JSONObject> findRateForCurrency(JSONArray jsonArray, String currency) {
        return IntStream.range(0, jsonArray.length())
                .mapToObj(jsonArray::getJSONObject)
                .filter(jsonObject -> jsonObject.getString("cc").equals(currency))
                .findFirst();
    }
}
