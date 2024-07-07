package main;

import io.FileManager;
import io.InputProcessor;
import io.OutputProcessor;
import ui.AppWindow;

import javax.swing.*;

public class App {
	private static final FileManager FILE_MANAGER = new FileManager();
	private static final InputProcessor INPUT_PROCESSOR = new InputProcessor();
	private static final OutputProcessor OUTPUT_PROCESSOR = new OutputProcessor();
	private static final AppWindow APP_WINDOW = new AppWindow();

	public static void main(String[] args) {
		// First, the app has to make sure the input and output directories are existent
		FILE_MANAGER.createIOFiles();

		// Program UI
		SwingUtilities.invokeLater(() -> {
			APP_WINDOW.setVisible(true);
		});

		for (;;) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			APP_WINDOW.repaintQueuedComponents();
		}
	}

	public static FileManager getFileManager() {
		return FILE_MANAGER;
	}

	public static InputProcessor getInputProcessor() {
		return INPUT_PROCESSOR;
	}

	public static OutputProcessor getOutputProcessor() {
		return OUTPUT_PROCESSOR;
	}

	public static AppWindow getAppWindow() {
		return APP_WINDOW;
	}
}
