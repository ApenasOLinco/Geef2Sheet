package io;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class FileManager {
	private final String INPUT_PATH = "io/input";
	private final String OUTPUT_PATH = "io/output";
	private final File INPUT_FOLDER = new File(INPUT_PATH);
	private final File OUTPUT_FOLDER = new File(OUTPUT_PATH);
	
	public void createIOFiles() {
		if (!INPUT_FOLDER.exists())
			System.out.println(INPUT_FOLDER.mkdirs());
		
		if (!OUTPUT_FOLDER.exists())
			System.out.println(OUTPUT_FOLDER.mkdirs());
	}
	
	public Image getFileAsImage(File file) {
		Image image;
		
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return image;
	}
	
	public void createFiles(File... files) {
		Arrays.stream(files).forEach(file -> file.mkdirs());
	}
	
	public String getInputPath() {
		return INPUT_PATH;
	}
	
	public String getOutputPath() {
		return OUTPUT_PATH;
	}
	
	public File getInputFolder() {
		return INPUT_FOLDER;
	}
	
	public File getOutputFolder() {
		return OUTPUT_FOLDER;
	}
	
}
