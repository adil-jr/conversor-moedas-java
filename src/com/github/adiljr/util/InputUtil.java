package com.github.adiljr.util;

import com.github.adiljr.model.Currency;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class InputUtil {
    private final Scanner scanner;

    public InputUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getIntInput(String prompt) {
        int input = -1;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            try {
                input = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return input;
    }

    public BigDecimal getAmountInput(String prompt) {
        BigDecimal amount = BigDecimal.ZERO;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                amount = new BigDecimal(line);
                if (amount.compareTo(BigDecimal.ZERO) > 0) {
                    validInput = true;
                } else {
                    System.out.println("O valor deve ser positivo. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número decimal válido (ex: 100.50).");
            }
        }
        return amount;
    }

    public Currency getCurrencySelection(String prompt) {
        Optional<Currency> selectedCurrency = Optional.empty();
        while (selectedCurrency.isEmpty()) {
            Currency.displayAvailableCurrencies();
            int choice = getIntInput(prompt + " (digite o número): ");
            selectedCurrency = Currency.fromNumber(choice);
            if (selectedCurrency.isEmpty()) {
                System.out.println("Opção de moeda inválida. Tente novamente.");
            }
        }
        return selectedCurrency.get();
    }
}
