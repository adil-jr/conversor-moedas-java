package com.github.adiljr.model;

import java.util.Arrays;
import java.util.Optional;

public enum Currency {

    USD("USD", "Dólar Americano"),
    EUR("EUR", "Euro"),
    BRL("BRL", "Real Brasileiro"),
    GBP("GBP", "Libra Esterlina"),
    JPY("JPY", "Iene Japonês"),
    CAD("CAD", "Dólar Canadense"),
    AUD("AUD", "Dólar Australiano"),
    CHF("CHF", "Franco Suíço"),
    CNY("CNY", "Yuan Chinês"),
    MXN("MXN", "Peso Mexicano");

    private final String code;
    private final String description;

    Currency(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }

    public static Optional<Currency> fromCode(String code) {
        return Arrays.stream(Currency.values())
                .filter(currency -> currency.code.equalsIgnoreCase(code))
                .findFirst();
    }

    public static Optional<Currency> fromNumber(int number) {
        if (number >= 1 && number <= values().length) {
            return Optional.of(values()[number - 1]);
        }
        return Optional.empty();
    }

    public static void displayAvailableCurrencies() {
        System.out.println("Moedas disponíveis para conversão");
        for (Currency currency : Currency.values()) {
            System.out.printf("%d. %s (%s)\n", currency.ordinal() + 1, currency.getDescription(), currency.getCode());
        }
    }
}
