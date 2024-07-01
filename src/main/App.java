package main;

import ui.AppWindow;

import javax.swing.*;
import java.io.File;

public class App {
    private static final String INPUT_PATH = "io/input";
    private static final String OUTPUT_PATH = "io/output";
    private static final File INPUT_FOLDER = new File(INPUT_PATH);
    private static final File OUTPUT_FOLDER = new File(OUTPUT_PATH);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final AppWindow appWindow = new AppWindow();
            appWindow.setVisible(true);
        });
//
//        boolean exit = false;
//
//        if (!INPUT_FOLDER.exists()) {
//            System.out.println(INPUT_FOLDER.mkdirs());
//            exit = true;
//        }
//        if (!OUTPUT_FOLDER.exists()) {
//            System.out.println(OUTPUT_FOLDER.mkdirs());
//            exit = true;
//        }
//
//        if(exit) System.exit(0);
//
//        InputManager inputManager = new InputManager();
//        inputManager.process();
    }

    public static File getInputFolder() {
        return INPUT_FOLDER;
    }

    public static File getOutputFolder() {
        return OUTPUT_FOLDER;
    }
}
