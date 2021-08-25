package com.vwapapplication;

import com.vwapapplication.gui.GUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
         //launch gui
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
    }

    private static void createGUI() {
        GUI gui = new GUI(); //main ui class built using form designer
    }

}


