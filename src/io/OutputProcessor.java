package io;

import static java.lang.StringTemplate.STR;
import static main.App.getFileManager;

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
	public void process(ArrayList<File> files) {
		ArrayList<File> processedFiles = new ArrayList<>(files.size());
		
		for(int i = 0; i < files.size(); i ++) {
			File inputFile = files.get(i);
			File outputFile = 
				new File(STR."\{App.getFileManager().getOutputPath()}/\{inputFile .getName().replaceAll("\\.[a-zA-Z]+", "")} Frames.\{OutputConfigurations.getFileFormat()}");
			
			if (getFileManager().getCachedOutputFiles().containsKey(outputFile.getName()))
				continue;
			
			var asImageArray = getGifFileAsImageArray(inputFile);
			if(asImageArray != null)
				writeImagesToFile(asImageArray, outputFile);
			
			processedFiles.add(outputFile);
		}

		if (processedFiles.size() > 0)
			App.getIONotifier().notifyObservers(io.event.IONotifier.EventType.FILES_PROCESSED, processedFiles.toArray(new File[0]));
	}
	
	private Image[] getGifFileAsImageArray(File file) {
		try {
			ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
			
			ImageInputStream input = ImageIO.createImageInputStream(file);
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
			// Configurations
			int imagesPerLine = OutputConfigurations.getNumberOfColumns();
			String format = OutputConfigurations.getFileFormat();
			
			int hGap = OutputConfigurations.gethGap();
			int vGap = OutputConfigurations.getvGap();
			int imageWidth = imgs[0].getWidth(null);
			int imageHeight = imgs[0].getHeight(null);
			int lineWidth = imageWidth * Math.min(imagesPerLine, imgs.length) + hGap * imagesPerLine;
			int lineHeight = imageHeight * (int) Math.ceil((double) imgs.length / imagesPerLine);
			
			BufferedImage outputImage = new BufferedImage(lineWidth, lineHeight, BufferedImage.TYPE_INT_ARGB);
			
			for (int i = 0; i < imgs.length; i++) {
				Image img = imgs[i];
				
				int currentImageInLine = i % imagesPerLine;
				int currentLine = i / imagesPerLine;
				int x = currentImageInLine * imageWidth + hGap * currentImageInLine;
				int y = currentLine * imageHeight + vGap * currentLine;
				
				outputImage.getGraphics().drawImage(img, x, y, null);
			}
			
			ImageWriter writer = ImageIO.getImageWritersByFormatName(format).next();
			ImageOutputStream output = ImageIO.createImageOutputStream(outputFile);
			writer.setOutput(output);
			writer.write(outputImage);
			output.close();
			writer.dispose();
			getFileManager().cacheFile(outputFile);
		} catch (IOException e) {
			System.err.println(STR."Coudn't write file \{outputFile}: \n\{e.getMessage()} \n\{e.getStackTrace()}");
		}
	}
}
