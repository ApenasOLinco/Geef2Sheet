package com.apenasolinco.geef2sheet_api.service.impl;

import com.apenasolinco.geef2sheet_api.model.OutputConfigurations;
import com.apenasolinco.geef2sheet_api.service.GifService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

@Service
public class GifServiceImpl implements GifService {

	@Override
	public byte[] gifToSheet(MultipartFile gif, OutputConfigurations configurations) {
		Image[] frames = getFrames(gif);

		return getSheet(configurations, frames);
	}

	private byte[] getSheet(OutputConfigurations configurations, Image[] frames) {
		File resultFile = new File("/results/result." + configurations.getOutputFormat());

		int inputWidth = frames[0].getWidth(null);
		int inputHeight = frames[0].getHeight(null);

		int gaplessOutputWidth = inputWidth * Math.min(configurations.getNumberOfColumns(), frames.length);
		int outputWidth = gaplessOutputWidth + configurations.getHorizontalGap() * configurations.getNumberOfColumns();

		int numberOfLines = (int) Math.ceil((double) frames.length / configurations.getNumberOfColumns());
		int gaplessOutputHeight = inputHeight * numberOfLines;
		int outputHeight = gaplessOutputHeight + configurations.getVerticalGap() * numberOfLines;

		BufferedImage outputBuffer = new BufferedImage(outputWidth, outputHeight, BufferedImage.TYPE_INT_ARGB);

		try (
			var outputStream = ImageIO.createImageOutputStream(resultFile)
		) {

			for (int i = 0; i < frames.length; i++) {
				Image frame = frames[i];

				int positionInLine = i % configurations.getNumberOfColumns();
				int currentLine = i / configurations.getNumberOfColumns();
				int x = positionInLine * inputWidth + configurations.getHorizontalGap() * positionInLine;
				int y = currentLine * inputHeight + configurations.getVerticalGap() * currentLine;

				outputBuffer.getGraphics().drawImage(frame, x, y, null);
			}

			var imageWriter = ImageIO.getImageWritersByFormatName(configurations.getOutputFormat()).next();
			imageWriter.setOutput(outputStream);
			imageWriter.write(outputBuffer);

			imageWriter.dispose();

			byte[] result = new byte[(int) resultFile.getTotalSpace()];
			IOUtils.readFully(new FileInputStream(resultFile), result);

			return result;
		} catch (IOException | NoSuchElementException e) {
			throw new RuntimeException(e);
		}
	}

	private Image[] getFrames(MultipartFile gif) {
		Image[] frames;

		try (var inputStream = gif.getInputStream()) {
			var reader = ImageIO.getImageReadersByFormatName("gif").next();
			reader.setInput(inputStream, false);

			var gifLength = reader.getNumImages(true);
			frames = new Image[gifLength];

			for (int i = 0; i < gifLength; i++) {
				frames[i] = reader.read(i);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return frames;
	}
}
