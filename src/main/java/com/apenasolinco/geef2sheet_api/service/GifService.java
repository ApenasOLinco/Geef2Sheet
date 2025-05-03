package com.apenasolinco.geef2sheet_api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public interface GifService {

	File gifToSheet(MultipartFile gif);

}
