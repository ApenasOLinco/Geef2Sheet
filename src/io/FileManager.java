package io;

import java.io.File;
import java.util.Arrays;

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
