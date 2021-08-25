package com.vwapapplication.services;

import com.vwapapplication.model.Trade;
import org.apache.commons.math3.util.Precision;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * VWAPCalculator's purpose is to contain static Methods to be used on a List of trade Objects.
 */
public class VWAPCalculator {

    /**
     * The calculateStockVWAP Method uses Streams to calculate the Volume Weighted Average Price of a stock after filtering for the epic of a stock.
     * @param trades - A List of trade Objects.
     * @param epic - The shorthand name of a stock.
     * @return the Volume Weighted Average Price given the epic of a stock.
     */
    public static double calculateStockVWAP(List<Trade> trades, String epic) {
        List<Trade> filteredTrades = trades
                .stream()
                .filter(trade -> trade.getEpic().equals(epic))
                .toList();
        return calculateVWAP(filteredTrades);
    }

    /**
     * The calculateStockTradeTypeVWAP Method uses Streams to calculate the Volume Weighted Average Price of a stock after filtering for the epic of a stock
     * and grouping the calculation by Trade Type.
     * @param trades - A List of trade Objects.
     * @param epic - The shorthand name of a stock.
     * @return a Map of the Volume Weighted Average Price of a stock given the epic, per Trade Type.
     */
    public static HashMap<String, Double> calculateStockTradeTypeVWAP(List<Trade> trades, String epic) {
        Map<String, List<Trade>> groupedTrades = trades
                .stream()
                .filter(trade -> trade.getEpic().equals(epic))
                .collect(Collectors.groupingBy(Trade::getTradeType));
        HashMap<String, Double> tradeTypeVWAPs = new HashMap<>();

        for (String tradeType : groupedTrades.keySet()) {
            tradeTypeVWAPs.put(tradeType + " VWAP", calculateVWAP(groupedTrades.get(tradeType)));
        }
        return tradeTypeVWAPs;
    }

    /**
     * The calculateVWAP method uses Streams to calculate the Volume Weighted Average Price given a list of filtered trades.
     * @param filteredTrades - A List of Trade Objects which have been filtered.
     * @return a rounded double of the Volume Weighted Average Price of a stock.
     */
    private static double calculateVWAP(List<Trade> filteredTrades) {
        double totalValueOfShares = filteredTrades
                .stream()
                .mapToDouble(trade -> trade.getQuantity() * trade.getPrice())
                .sum();
        int totalVolumeOfShares = filteredTrades
                .stream()
                .mapToInt(Trade::getQuantity)
                .sum();
        return Precision.round((totalValueOfShares / totalVolumeOfShares), 2);
    }
}
