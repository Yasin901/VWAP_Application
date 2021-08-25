package com.vwapapplication.gui;

import com.vwapapplication.services.CsvFileWriter;
import com.vwapapplication.services.JsonFileWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.HashMap;

/**
 * This utility class exposes a method to handle the saving of a  file.
 * The method handleFileSave takes a file extension and a hashmap called data and decides how to save the file based on the users action.
 */
public class FileHandler {

    public static void handleFileSave(String extension, HashMap<String, Double> data) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*." + extension, extension);
        fileChooser.setDialogTitle("Save");
        fileChooser.setApproveButtonText("Save");
        fileChooser.setFileFilter(filter);

        int saveOutcome = fileChooser.showOpenDialog(null);

        if (saveOutcome == JFileChooser.APPROVE_OPTION) {
            if (extension.equals("csv")) {
                saveCsv(fileChooser.getSelectedFile(), data);
            }

            if (extension.equals("json")) {
                saveJson(fileChooser.getSelectedFile(), data);
            }
        }
    }

    private static void saveCsv(File file, HashMap<String, Double> data) {
        String extension = getFileExtension(file);
        String filePath = file.getAbsolutePath();

        if (extension.equals("")) {
            filePath += ".csv";
        }
        CsvFileWriter.writeFile(filePath, data);
    }

    private static void saveJson(File file, HashMap<String, Double> data) {
        String extension = getFileExtension(file);
        String filePath = file.getAbsolutePath();

        if (extension.equals("")) {
            filePath += ".json";
        }
        JsonFileWriter.writeFile(filePath, data);
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }
}
