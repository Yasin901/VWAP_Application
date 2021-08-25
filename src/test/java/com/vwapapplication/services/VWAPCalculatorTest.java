package com.vwapapplication.services;

import com.vwapapplication.model.Trade;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class VWAPCalculatorTest {
    @Test
    public void shouldCalculateStockVWAP() {
        List<Trade> trades = List.of(
                new Trade("CXRB", "GK8838181522", "GK8838181522-U-25", "Uncrossing", 2359, .7205),
                new Trade("CXRB", "GK8838181522", "GK8838181522-A-14", "Automatic", 1000, .603),
                new Trade("CXRB", "GK8838181522", "GK8838181522-O-03", "Ordinary", 3333, .696685),
                new Trade("CXRB", "GK8838181522", "GK8838181522-L-07", "Large", 13334, .726),
                new Trade("CXRB", "GK8838181522", "GK8838181522-O-18", "Ordinary", 315, .558999)
        );
        assertThat(VWAPCalculator.calculateStockVWAP(trades, "CXRB")).isEqualTo(0.71);
    }

    @Test
    public void shouldCalculateStockTradeTypeVWAP() {
        List<Trade> trades = List.of(
                new Trade("CXRB", "GK8838181522", "GK8838181522-U-25", "Uncrossing", 2359, .7205),
                new Trade("CXRB", "GK8838181522", "GK8838181522-A-14", "Automatic", 1000, .603),
                new Trade("CXRB", "GK8838181522", "GK8838181522-O-03", "Ordinary", 3333, .696685),
                new Trade("CXRB", "GK8838181522", "GK8838181522-L-07", "Large", 13334, .726),
                new Trade("CXRB", "GK8838181522", "GK8838181522-O-18", "Ordinary", 315, .558999)
        );
        Map<String, Double> result = VWAPCalculator.calculateStockTradeTypeVWAP(trades, "CXRB");
        assertThat(result.get("Uncrossing VWAP")).isEqualTo(0.72);
        assertThat(result.get("Automatic VWAP")).isEqualTo(0.60);
        assertThat(result.get("Ordinary VWAP")).isEqualTo(0.68);
        assertThat(result.get("Large VWAP")).isEqualTo(0.73);
    }


}