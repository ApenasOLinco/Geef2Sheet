package io;

import static java.lang.StringTemplate.STR;

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
	private static final int IMAGES_PER_LINE = 3;
	private static final String FORMAT = "png";

	public void process(ArrayList<File> files) {
		// @formatter:off
		files.forEach(file -> {
			File outputFile = new File(STR."\{App.getFileManager().getOutputPath()}/\{file.getName()} Frames.\{FORMAT}");
			var asImageArray = getAsImageArray(file);
			
			if(asImageArray != null) {
				writeImagesToFile(asImageArray, outputFile);
			}
		});
		// @formatter:on
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
		} catch(IOException e) {
			System.err.println(STR."Couldn't convert file to an image array: \{e.getMessage()}\n\{e.getStackTrace()}");
			return null;
		}
	}

	private void writeImagesToFile(Image[] imgs, File outputFile) {
		try {
		outputFile.createNewFile();

		int imageWidth = imgs[0].getWidth(null);
		int imageHeight = imgs[0].getHeight(null);
		int lineWidth = imageWidth * IMAGES_PER_LINE;
		int lineHeight = imageHeight * (int) Math.ceil((double) imgs.length / IMAGES_PER_LINE);

		BufferedImage outputImage = new BufferedImage(lineWidth, lineHeight, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < imgs.length; i++) {
			Image img = imgs[i];

			int x = (i % IMAGES_PER_LINE) * imageWidth;
			int y = (i / IMAGES_PER_LINE) * imageHeight;

			outputImage.getGraphics().drawImage(img, x, y, null);
		}

		ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
		ImageOutputStream output = ImageIO.createImageOutputStream(outputFile);
		writer.setOutput(output);
		writer.write(outputImage);
		output.close();
		writer.dispose();
		} catch (IOException e) {
			System.err.println(STR."Coudn't write file \{outputFile}: \n\{e.getMessage()} \n\{e.getStackTrace()}");
		}
	}
}
