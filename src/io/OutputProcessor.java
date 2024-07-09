package io;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import main.App;

public class OutputProcessor {
	private int imagesPerLine = App.getOutputConfigurations().getNumberOfColumns();
	private String format = App.getOutputConfigurations().getFileFormat();
	private final ArrayList<File> outputFiles = new ArrayList<>();
	
	public void process(ArrayList<File> files) {
		ArrayList<File> processedFiles = new ArrayList<>(files.size());
		
		// @formatter:off
		for(int i = 0; i < files.size(); i ++) {
			File file = files.get(i);
			File outputFile = new File(STR."\{App.getFileManager().getOutputPath()}/\{file.getName()} Frames.\{format}");
			
			if (outputFiles.contains(outputFile))
				continue;
			
			var asImageArray = getAsImageArray(file);
			if(asImageArray != null)
				writeImagesToFile(asImageArray, outputFile);
			
			processedFiles.add(outputFile);
		}
		// @formatter:on
		if (processedFiles.size() > 0)
			App.getIONotifier().notifyObservers(io.event.IONotifier.EventType.FILES_PROCESSED,
			processedFiles.toArray(new File[0]));
	}
	
	private Image[] getAsImageArray(File file) {
		try {
			ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
			
			ImageInputStream input;
			input = ImageIO.createImageInputStream(file);
			reader.setInput(input, false);
			
			int gifLength = reader.getNumImages(true);
			Image[] images = new Image[gifLength];
			
			for (int i = 0; i < gifLength; i++) {
				BufferedImage image = reader.read(i);
				images[i] = image;
				System.out.println(STR."\{image.getWidth()}x\{image.getHeight()}");
			}
			
			return images;
		} catch (IOException e) {
			System.err.println(STR."Couldn't convert file to an image array: \{e.getMessage()}\n\{e.getStackTrace()}");
			return null;
		}
	}
	
	private void writeImagesToFile(Image[] imgs, File outputFile) {
		try {
			int imageWidth = imgs[0].getWidth(null);
			int imageHeight = imgs[0].getHeight(null);
			int lineWidth = imageWidth * imagesPerLine;
			int lineHeight = imageHeight * (int) Math.ceil((double) imgs.length / imagesPerLine);
			
			BufferedImage outputImage = new BufferedImage(lineWidth, lineHeight, BufferedImage.TYPE_INT_ARGB);
			
			for (int i = 0; i < imgs.length; i++) {
				Image img = imgs[i];
				
				int x = (i % imagesPerLine) * imageWidth;
				int y = (i / imagesPerLine) * imageHeight;
				
				outputImage.getGraphics().drawImage(img, x, y, null);
			}
			
			ImageWriter writer = ImageIO.getImageWritersByFormatName(format).next();
			ImageOutputStream output = ImageIO.createImageOutputStream(outputFile);
			writer.setOutput(output);
			writer.write(outputImage);
			output.close();
			writer.dispose();
			getOutputFiles().add(outputFile);
		} catch (IOException e) {
			System.err.println(STR."Coudn't write file \{outputFile}: \n\{e.getMessage()} \n\{e.getStackTrace()}");
		}
	}
	
	public ArrayList<File> getOutputFiles() {
		return outputFiles;
	}
}
