package com.github.adiljr;

import com.github.adiljr.model.ConversionRecord;
import com.github.adiljr.model.Currency;
import com.github.adiljr.model.ExchangeRateApiResponse;
import com.github.adiljr.service.ApiService;
import com.github.adiljr.service.HistoryService;
import com.github.adiljr.service.LogService;
import com.github.adiljr.util.InputUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private final Scanner scanner;
    private final InputUtil inputUtil;
    private final ApiService apiService;
    private final HistoryService historyService;
    private final LogService logService;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.inputUtil = new InputUtil(scanner);
        this.apiService = new ApiService();
        this.historyService = new HistoryService();
        this.logService = new LogService();
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.github.adiljr.util.ConfigUtil").getMethod("getApiKey").invoke(null);
        } catch (Exception e) {
            if (e.getCause() instanceof IllegalStateException) {
                System.err.println("Falha ao iniciar: " + e.getCause().getMessage());
            } else {
                System.err.println("Ocorreu um erro inesperado ao verificar a configuração da API Key: " + e.getMessage());
            }
            System.out.println("Encerrando a aplicação.");
            return;
        }

        try {
            Class.forName("com.google.gson.Gson");
        } catch (ClassNotFoundException e) {
            System.err.println("ERRO CRÍTICO: A biblioteca GSON não foi encontrada no classpath.");
            System.err.println("Por favor, adicione o arquivo gson.jar ao diretório 'lib' e configure o classpath.");
            System.err.println("Consulte o README.md para instruções de compilação e execução.");
            System.out.println("Encerrando a aplicação.");
            return;
        }

        Main app = new Main();
        app.run();
    }

    public void run() {
        System.out.println("Bem-vindo ao Conversor de Moedas!");
        boolean running = true;

        while (running) {
            printMenu();
            int choice = inputUtil.getIntInput("Escolha uma opção: ");

            switch (choice) {
                case 1:
                    performConversion();
                    break;
                case 2:
                    historyService.displayHistory();
                    break;
                case 3:
                    running = false;
                    System.out.println("Obrigado por usar o Conversor de Moedas. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            if (running) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Converter Moeda");
        System.out.println("2. Ver Histórico de Conversões (desta sessão)");
        System.out.println("3. Sair");
        System.out.println("----------------------");
        System.out.println("(Os logs de todas as conversões são salvos em conversion_logs.txt)");
    }

    private void performConversion() {
        System.out.println("\n--- Nova Conversão ---");

        Currency fromCurrency = inputUtil.getCurrencySelection("Escolha a moeda de ORIGEM");
        Currency toCurrency = inputUtil.getCurrencySelection("Escolha a moeda de DESTINO");
        BigDecimal amount = inputUtil.getAmountInput("Digite o valor a ser convertido (" + fromCurrency.getCode() + "): ");

        System.out.println("\nBuscando taxa de câmbio para " + fromCurrency.getCode() + " -> " + toCurrency.getCode() + "...");

        Optional<ExchangeRateApiResponse> ratesResponseOpt = apiService.getLatestRates(fromCurrency);

        if (ratesResponseOpt.isEmpty()) {
            System.out.println("Não foi possível obter as taxas de câmbio. Verifique sua conexão ou a configuração da API Key.");
            return;
        }

        ExchangeRateApiResponse ratesResponse = ratesResponseOpt.get();
        Optional<BigDecimal> specificRateOpt = apiService.getSpecificRate(ratesResponse, toCurrency);

        if (specificRateOpt.isEmpty()) {
            System.out.println("Não foi possível encontrar a taxa de câmbio para " + toCurrency.getCode() + " a partir de " + fromCurrency.getCode() + ".");
            System.out.println("Resposta da API: " + ratesResponse.toString()); // For debugging
            return;
        }

        BigDecimal exchangeRate = specificRateOpt.get();
        BigDecimal convertedAmount = amount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);

        System.out.println("\n--- Resultado da Conversão ---");
        System.out.printf("Valor Original: %.2f %s\n", amount, fromCurrency.getCode());
        System.out.printf("Moeda Destino: %s\n", toCurrency.getDescription());
        System.out.printf("Taxa de Câmbio: 1 %s = %.4f %s\n", fromCurrency.getCode(), exchangeRate, toCurrency.getCode());
        System.out.printf("Valor Convertido: %.2f %s\n", convertedAmount, toCurrency.getCode());
        System.out.println("-----------------------------");

        ConversionRecord record = new ConversionRecord(fromCurrency, toCurrency, amount, exchangeRate, convertedAmount);
        historyService.addRecord(record);
        logService.logConversion(record);
    }
}
