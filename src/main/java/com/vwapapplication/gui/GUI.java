package com.vwapapplication.gui;

import com.vwapapplication.model.Trade;
import com.vwapapplication.services.CsvFileReader;
import com.vwapapplication.services.VWAPCalculator;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GUI {

    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public Class<?> getColumnClass(int column) {
            return switch (column) {
                case 4 -> Integer.class;
                case 5 -> Double.class;
                default -> String.class;
            };
        }
    };
    private JLabel vwapInformation = new JLabel();
    private JButton saveAsCsv = new JButton("Save as CSV");
    private JButton saveAsJson = new JButton("Save as JSON");
    private JComboBox<String> comboBox = new JComboBox<>();
    private JPanel panel;

    /**
     * This method sets up the layout of the GUI by using Swing parameters such as a JPanel and a JTable.
     */
    public GUI() {
        CsvFileReader csvFileReader = new CsvFileReader("src/main/resources/market_trades.csv");
        List<Trade> trades = csvFileReader.readFile();

        JFrame frame = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Market Trades", TitledBorder.CENTER, TitledBorder.TOP));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        comboBox = createComboBox(trades);
        comboBox.addActionListener(e -> handleTableChange(trades));
        panel.add(comboBox, BorderLayout.NORTH);

        panel.add(vwapInformation, BorderLayout.NORTH);

        saveAsCsv.setEnabled(false);
        saveAsJson.setEnabled(false);
        panel.add(saveAsCsv);
        panel.add(saveAsJson);

        updateTableModel(trades);

        JTable table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setSize(700, 400);
        frame.setTitle("Market Trades Viewer");
        frame.setVisible(true);
    }

    /**
     * This method is an event listener that is attached to the combo box(drop-down list) to handle updating the table with trades.
     * This is done by selecting a particular epic in a drop-down list to filter for and displaying a newly updated table.
     */
    private void handleTableChange(List<Trade> trades) {
        String selectedEpic = comboBox.getSelectedItem().toString();

        if (selectedEpic.equals("All")) {
            updateTableModel(trades);
            vwapInformation.setText("");

            saveAsCsv.setEnabled(false);
            saveAsJson.setEnabled(false);
        } else {
            List<Trade> filteredTrades = trades.stream().filter(trade -> trade.getEpic().equals(selectedEpic)).toList();
            updateTableModel(filteredTrades);

            HashMap<String, Double> calculations = VWAPCalculator.calculateStockTradeTypeVWAP(trades, selectedEpic);
            calculations.putAll(Collections.singletonMap("All trade type VWAP", VWAPCalculator.calculateStockVWAP(trades, selectedEpic)));

            vwapInformation.setText(calculations.toString());

            saveAsCsv.setEnabled(true);
            saveAsJson.setEnabled(true);
            saveAsCsv.addActionListener(e -> FileHandler.handleFileSave("csv", calculations));
            saveAsJson.addActionListener(e -> FileHandler.handleFileSave("json", calculations));
        }
    }

    /**
     * This function takes a list of trades and updates the table.
     */
    private void updateTableModel(List<Trade> trades) {
        String[] headers = buildHeaders(trades.get(0));
        Object[][] data = buildData(trades, headers.length);
        tableModel.setDataVector(data, headers);
    }

    /**
     * This method creates and returns a combo box consisting of all epics from the market trade data.
     */
    private JComboBox<String> createComboBox(List<Trade> trades) {
        String[] epics = trades.stream().map(Trade::getEpic).distinct().toList().toArray(new String[0]);
        JComboBox<String> comboBox = new JComboBox<>(epics);
        comboBox.addItem("All");
        comboBox.setSelectedItem("All");
        comboBox.setMaximumSize(new Dimension(100, 100));
        comboBox.setAlignmentX(0);

        return comboBox;
    }

    /**
     * This method returns the headers for the table based on the shape of the Trade object that it receives. This uses reflection to identify the class fields as headers.
     */
    private String[] buildHeaders(Trade trade) {
        Field[] fields = trade.getClass().getDeclaredFields();
        String[] headers = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            headers[i] = fields[i].getName();
        }
        return headers;
    }

    /**
     * This method returns the data (rows) from a list of trades that will be used to populate the table.
     */
    private Object[][] buildData(List<Trade> trades, int headerSize) {
        Object[][] data = new Object[trades.size()][headerSize];

        for (int i = 0; i < trades.size(); i++) {
            data[i] = new Object[]{trades.get(i).getEpic(), trades.get(i).getIsin(), trades.get(i).getTradeRef(), trades.get(i).getTradeType(), trades.get(i).getQuantity(), trades.get(i).getPrice()};
        }
        return data;
    }
}