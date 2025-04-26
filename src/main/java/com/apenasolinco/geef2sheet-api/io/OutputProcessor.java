package io;

import static io.event.IONotifier.EventType.FILES_PROCESSED;
import static java.lang.StringTemplate.STR;
import static main.App.getFileManager;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import main.App;

public class OutputProcessor {
	
	public void process(ArrayList<File> files) {
		Thread processingThread = new Thread(() -> {
			// Sort the files by length so the smaller ones are processed first
			files.sort((f1, f2) -> Long.compare(f1.length(), f2.length()));
			startProcess(files);
		});
	
		processingThread.start();
	}

	private void startProcess(ArrayList<File> files) {
		ArrayList<Image[]> gifs = new ArrayList<>(files.size());
		
		// Get the Image Array from the content of each file
		for(int i = 0; i < files.size(); i++) {
			Optional<Image[]> asImgArray = getGifFileAsImageArray(files.get(i));
			
			if(asImgArray.isEmpty()) continue;

			gifs.add(i, asImgArray.get());
		}
		
		// Create output files and write to them
		for(int i = 0; i < files.size(); i++) {
			File inputFile = files.get(i);
			String fileName = inputFile.getName();
			Image[] gif = gifs.get(i);
			
			@SuppressWarnings("preview")
			File outputFile = new File(
				STR."\{App.getFileManager().getOutputPath()}/\{fileName.replaceAll("\\.[a-zA-Z]+", "")} Frames.\{OutputConfigurations.getFileFormat()}"
			);
			
			if (getFileManager().getCachedOutputFiles().containsKey(outputFile.getName()))
				return;
			
			if(writeImagesToFile(gif, outputFile))
				App.getIONotifier().notifyObservers(FILES_PROCESSED, outputFile);
		}
	}
	
	private Optional<Image[]> getGifFileAsImageArray(File file) {
		// Return variable
		Image[] images = null;
		
		try {
			ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
			
			ImageInputStream input = ImageIO.createImageInputStream(file);
			reader.setInput(input, false);
			
			int gifLength = reader.getNumImages(true);
			images = new Image[gifLength];
			
			// Stores the images in the array
			for (int i = 0; i < gifLength; i++) {
				BufferedImage image = reader.read(i);
				images[i] = image;
				System.out.println(STR."\{image.getWidth()}x\{image.getHeight()}");
			}
		} catch (IOException e) {
			System.err.println(STR."Couldn't convert file to an image array: \{e.getMessage()}\n\{e.getStackTrace()}");
		}
		
		return Optional.ofNullable(images);
	}
	
	private boolean writeImagesToFile(Image[] imgs, File outputFile) {
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
			
			return true;
		} catch (IOException e) {
			System.err.println(STR."Coudn't write file \{outputFile}: \n\{e.getMessage()} \n\{e.getStackTrace()}");
			return false;
		}
	}
}
