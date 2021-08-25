package com.vwapapplication.services;

import com.opencsv.CSVReader;
import com.vwapapplication.model.Trade;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * CsvFileReader's purpose is to read a trade file and produce a List of trade Objects, representing the contents of the file.
 */
public class CsvFileReader {

    private static String fileName;

    public CsvFileReader(String fileName) {
        CsvFileReader.fileName = fileName;
    }

    /**
     * The readFile method reads each line of a file and stores all values within an Array, skipping the first column headings line.
     * A new object is created from these values and added to a List of Objects.
     * @return a list of trade Objects.
     */
    public List<Trade> readFile() {
        List<Trade> trades = new ArrayList<Trade>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(fileName));
            csvReader.readNext();
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                trades.add(new Trade(values[0], values[1], values[2], values[3], Integer.parseInt(values[4]), Double.parseDouble(values[5])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trades;
    }
}



