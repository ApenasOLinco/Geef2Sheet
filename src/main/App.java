package main;

import io.FileManager;
import io.InputManager;
import io.OutputManager;
import ui.AppWindow;

import javax.swing.*;

public class App {
	private static final FileManager FILE_MANAGER = new FileManager();
	private static final InputManager INPUT_MANAGER = new InputManager();
	private static final OutputManager OUTPUT_MANAGER = new OutputManager();
	private static final AppWindow appWindow = new AppWindow();

	public static void main(String[] args) {
		// First, the app has to make sure the input and output directories are existent
		FILE_MANAGER.createIOFiles();

		// Program UI
		SwingUtilities.invokeLater(() -> {
			appWindow.setVisible(true);
		});

		for (;;) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			appWindow.repaintQueuedComponents();
		}
	}

	public static FileManager getFileManager() {
		return FILE_MANAGER;
	}

	public static InputManager getInputManager() {
		return INPUT_MANAGER;
	}

	public static OutputManager getOutputManager() {
		return OUTPUT_MANAGER;
	}

	public static AppWindow getAppWindow() {
		return appWindow;
	}
}
