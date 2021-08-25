package com.vwapapplication.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;

/**
 * This utility class exposes the static method that takes a filename and a hashmap of data to save.
 * The method writeFile creates a file named from the provided filename and sets its contents as json format.
 */
public class JsonFileWriter {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void writeFile(String filePath, HashMap<String, Double> data) {
        try {
            mapper.writeValue(new File(filePath), data);
        } catch (Exception ignored) {

        }
    }

}
