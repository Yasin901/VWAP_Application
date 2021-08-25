package com.vwapapplication.services;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;

/**
 * This utility class exposes the static method that takes a filename and a hashmap of data to save.
 * The method writeFile creates a file named from the provided filename and sets its contents as csv format.
 */
public class CsvFileWriter {

    public static void writeFile(String filePath, HashMap<String, Double> data) {

        try {
            Writer writer = new FileWriter(filePath);
            CSVWriter csvWriter = new CSVWriter(writer);

            csvWriter.writeNext(data.keySet().toArray(new String[0]));
            csvWriter.writeNext(data.values().stream().map(Object::toString).toList().toArray(new String[0]));
            csvWriter.close();
        } catch (Exception ignored) {

        }
    }
}
