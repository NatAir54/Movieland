package com.nataliia.koval.movieland.conversion.impl;

import com.nataliia.koval.movieland.conversion.CurrencyConverter;
import com.nataliia.koval.movieland.exception.ConvertCurrencyException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class DefaultCurrencyConverter implements CurrencyConverter {
    private static final List<String> CURRENCIES_SUPPORTED = Arrays.asList("USD", "EUR");


    @Override
    public double convert(double price, String currency) {
        if (isInvalidCurrency(currency)) {
            throw new ConvertCurrencyException("Unsupported currency: " + currency + ". Price can be converted to USD or EUR.");
        }
        double exchangeRate = fetchExchangeRate(currency);
        double convertedPrice = price / exchangeRate;

        String formattedPrice = String.format(Locale.US, "%.2f", convertedPrice);

        return Double.parseDouble(formattedPrice);
    }

    private boolean isInvalidCurrency(String currency) {
        return !CURRENCIES_SUPPORTED.contains(currency.toUpperCase());
    }

    private double fetchExchangeRate(String currency) {
        try {
            String jsonResponse = fetchExchangeRateResponse(currency);
            double exchangeRate = parseExchangeRateFromJson(jsonResponse);

            if (exchangeRate <= 0) {
                throw new ConvertCurrencyException("Invalid exchange rate for currency: " + currency);
            }

            return exchangeRate;
        } catch (IOException | InterruptedException e) {
            throw new ConvertCurrencyException("Failed to fetch exchange rate for currency: " + currency, e);
        }
    }

    private String fetchExchangeRateResponse(String currency) throws IOException, InterruptedException {
        String EXCHANGE_RATE_BASE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=";
        HttpResponse<String> response;
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(EXCHANGE_RATE_BASE_URL + currency.toUpperCase() + "&json"))
                    .GET()
                    .build();

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        return response.body();
    }

    private double parseExchangeRateFromJson(String jsonResponse) {
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            return jsonObject.getDouble("rate");
        } catch (JSONException e) {
            throw new ConvertCurrencyException("Error parsing exchange rate from JSON: " + e.getMessage(), e);
        }
    }
}
