package io;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class FileManager {
	private final String INPUT_PATH = "io/input";
	private final String OUTPUT_PATH = "io/output";
	private final File INPUT_FOLDER = new File(INPUT_PATH);
	private final File OUTPUT_FOLDER = new File(OUTPUT_PATH);
	
	/**
	 * Caches the files processed by {@link io.OutputProcessor OutputProcessor} by associating their name with their path
	 */
	private final HashMap<String, File> cachedOutputFiles = new HashMap<>();
	
	public void createIOFiles() {
		if (!INPUT_FOLDER.exists())
			System.out.println(INPUT_FOLDER.mkdirs());
		
		if (!OUTPUT_FOLDER.exists())
			System.out.println(OUTPUT_FOLDER.mkdirs());
	}
	
	public void createFiles(File... files) {
		Arrays.stream(files).forEach(file -> file.mkdirs());
	}
	
	public void cacheFile(File file) {
		getCachedOutputFiles().put(file.getName(), file);
	}
	
	public HashMap<String, File> getCachedOutputFiles() {
		return cachedOutputFiles;
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