package com.apenasolinco.geef2sheet_api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

@Service
public interface GifService {

	Image gifToImage(MultipartFile gif);

}
