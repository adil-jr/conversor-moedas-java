package com.github.adiljr.service;

import com.github.adiljr.model.Currency;
import com.github.adiljr.model.ExchangeRateApiResponse;
import com.github.adiljr.util.ConfigUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ApiService {

    private static final String API_BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private final HttpClient httpClient;
    private final Gson gson;
    private final String apiKey;

    public ApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
        this.apiKey = ConfigUtil.getApiKey();
    }

    public Optional<ExchangeRateApiResponse> getLatestRates(Currency baseCurrency) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.err.println("API Key não configurada. Não é possível buscar taxas de câmbio.");
            return Optional.empty();
        }

        String url = API_BASE_URL + apiKey + "/latest/" + baseCurrency.getCode();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ExchangeRateApiResponse apiResponse = gson.fromJson(response.body(), ExchangeRateApiResponse.class);
                if (apiResponse != null && apiResponse.isSuccess()) {
                    return Optional.of(apiResponse);
                } else {
                    System.err.println("Erro na resposta da API: " + (apiResponse != null ? apiResponse.getErrorType() : "Resposta nula ou falha não especificada."));
                    System.err.println("Corpo da resposta (se houver erro): " + response.body());
                    return Optional.empty();
                }
            } else {
                System.err.println("Erro ao buscar taxas de câmbio. Status HTTP: " + response.statusCode());
                System.err.println("Corpo da resposta: " + response.body());
                return Optional.empty();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro de conexão ou interrupção ao chamar a API: " + e.getMessage());
            return Optional.empty();
        } catch (JsonSyntaxException e) {
            System.err.println("Erro ao parsear a resposta JSON da API: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<BigDecimal> getSpecificRate(ExchangeRateApiResponse ratesResponse, Currency targetCurrency) {
        if (ratesResponse == null || ratesResponse.getConversionRates() == null) {
            return Optional.empty();
        }
        BigDecimal rate = ratesResponse.getConversionRates().get(targetCurrency.getCode());
        return Optional.ofNullable(rate);
    }
}