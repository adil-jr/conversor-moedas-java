package com.github.adiljr.model;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeRateApiResponse {

    private String result;
    private String base_code;
    private Map<String, BigDecimal> conversion_rates;
    private String error_type;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBaseCode() {
        return base_code;
    }

    public void setBaseCode(String base_code) {
        this.base_code = base_code;
    }

    public Map<String, BigDecimal> getConversionRates() {
        return conversion_rates;
    }

    public void setConversionRates(Map<String, BigDecimal> conversion_rates) {
        this.conversion_rates = conversion_rates;
    }

    public String getErrorType() {
        return error_type;
    }

    public void setErrorType(String errorType) {
        this.error_type = errorType;
    }

    public boolean isSuccess() {
        return "success".equalsIgnoreCase(result);
    }
}
