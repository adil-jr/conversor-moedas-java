package com.github.adiljr.service;

import com.github.adiljr.model.ConversionRecord;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

public class LogService {

    private static final String LOG_FILE_NAME = "conversion_logs.txt";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public void logConversion(ConversionRecord record) {
        if (record == null) return;

        try (FileWriter fileWriter = new FileWriter(LOG_FILE_NAME, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            String logEntry = String.format(
                    "[%s] De: %s, Para: %s, Valor Original: %.2f, Taxa: %.4f, Valor Convertido: %.2f",
                    record.getTimestamp().format(TIMESTAMP_FORMATTER),
                    record.getFromCurrency().getCode(),
                    record.getToCurrency().getCode(),
                    record.getOriginalAmount(),
                    record.getExchangeRate(),
                    record.getConvertedAmount()
            );
            printWriter.println(logEntry);

        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log (" + LOG_FILE_NAME + "): " + e.getMessage());
        }
    }
}
