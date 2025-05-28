package com.github.adiljr.service;

import com.github.adiljr.model.ConversionRecord;

import java.util.LinkedList;

public class HistoryService {

    private static final int MAX_HISTORY_SIZE = 10;
    private final LinkedList<ConversionRecord> conversionHistory;

    public HistoryService() {
        this.conversionHistory = new LinkedList<>();
    }

    public void addRecord(ConversionRecord record) {
        if (record == null) return;

        if (conversionHistory.size() >= MAX_HISTORY_SIZE) {
            conversionHistory.removeFirst();
        }
        conversionHistory.addLast(record);
    }

    public void displayHistory() {
        if (conversionHistory.isEmpty()) {
            System.out.println("\nNenhuma conversão no histórico desta sessão ainda.");
            return;
        }

        System.out.println("\n--- Histórico de Conversões (Últimas " + conversionHistory.size() + ") ---");
        for (int i = conversionHistory.size() - 1; i >= 0; i--) {
            System.out.println(conversionHistory.get(i).toString());
        }
        System.out.println("-------------------------------------------");
    }

    public LinkedList<ConversionRecord> getConversionHistory() {
        return conversionHistory;
    }
}
