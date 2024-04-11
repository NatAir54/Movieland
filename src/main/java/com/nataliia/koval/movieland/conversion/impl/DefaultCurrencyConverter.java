package com.nataliia.koval.movieland.conversion.impl;

import com.nataliia.koval.movieland.conversion.CurrencyConverter;
import com.nataliia.koval.movieland.exception.ConvertCurrencyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

        return Double.parseDouble(String.format(Locale.US, "%.2f", convertedPrice));
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
        } catch (IOException e) {
            throw new ConvertCurrencyException("Failed to fetch exchange rate for currency: " + currency, e);
        }
    }

    private String fetchExchangeRateResponse(String currency) throws IOException {
        String EXCHANGE_RATE_BASE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=";
        URL url = new URL(EXCHANGE_RATE_BASE_URL + currency.toUpperCase() + "&json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
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
