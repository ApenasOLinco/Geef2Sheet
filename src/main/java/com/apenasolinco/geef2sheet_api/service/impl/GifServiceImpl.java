package com.apenasolinco.geef2sheet_api.service.impl;

import com.apenasolinco.geef2sheet_api.service.GifService;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GifServiceImpl implements GifService {
	@Override
	public Image gifToImage(MultipartFile gif) {
		Image result;

		try (var inputStream = gif.getInputStream()) {

			ImageIO.getImageReadersBySuffix("gif");

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return null;

	}
}
