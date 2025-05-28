package com.github.adiljr.util;

public class ConfigUtil {

    private static final String API_KEY_ENV_VAR = "EXCHANGERATE_API_KEY";
    private static String apiKey = null;

    public static String getApiKey() {
        if (apiKey == null) {
            apiKey = System.getenv(API_KEY_ENV_VAR);
            if (apiKey == null || apiKey.trim().isEmpty()) {
                System.err.println("ERRO CRÍTICO: A variável de ambiente " + API_KEY_ENV_VAR + " não está configurada.");
                System.err.println("Por favor, configure a variável de ambiente com sua API Key da ExchangeRate-API.");
                System.err.println("O programa não pode continuar sem a API Key.");
                throw new IllegalStateException("Variável de ambiente " + API_KEY_ENV_VAR + " não configurada.");
            }
        }
        return apiKey;
    }
}
