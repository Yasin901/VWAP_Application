package com.vwapapplication.services;

import com.vwapapplication.model.Trade;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class CsvFileReaderTest {

    @Test
    public void shouldReadTradesFromFile() {
        CsvFileReader csvFileReader = new CsvFileReader("src/test/resources/test_market_trades.csv");
        List<Trade> trades = csvFileReader.readFile();
        assertEquals(9, trades.size());
        Trade expectedTrade = new Trade("CXRB", "GK8838181522", "GK8838181522-A-14", "Automatic", 1000, .603);
        assertThat(trades).hasSize(9);
        assertThat(trades.get(1)).usingRecursiveComparison().isEqualTo(expectedTrade);
    }


}