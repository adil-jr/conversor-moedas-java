package com.github.adiljr.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionRecord {

    private final Currency fromCurrency;
    private final Currency toCurrency;
    private final BigDecimal originalAmount;
    private final BigDecimal exchangeRate;
    private final BigDecimal convertedAmount;
    private final LocalDateTime timestamp;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    public ConversionRecord(Currency fromCurrency, Currency toCurrency, BigDecimal originalAmount, BigDecimal exchangeRate, BigDecimal convertedAmount) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.originalAmount = originalAmount;
        this.exchangeRate = exchangeRate;
        this.convertedAmount = convertedAmount;
        this.timestamp = LocalDateTime.now();
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] Convertido: %.2f %s para %.2f %s (Taxa: %.4f %s/%s)",
                timestamp.format(FORMATTER),
                originalAmount,
                fromCurrency.getCode(),
                convertedAmount,
                toCurrency.getCode(),
                exchangeRate,
                toCurrency.getCode(),
                fromCurrency.getCode()
        );
    }

    public String toLogFormat() {
        return String.format("Timestamp: %s, De: %s, Para: %s, Valor Original: %.2f, Taxa: %.4f, Valor Convertido: %.2f",
                timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                fromCurrency.getCode(),
                toCurrency.getCode(),
                originalAmount,
                exchangeRate,
                convertedAmount
        );
    }
}
