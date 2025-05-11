package com.apenasolinco.geef2sheet_api.service;

import com.apenasolinco.geef2sheet_api.model.OutputConfigurations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface GifService {

	byte[] gifToSheet(MultipartFile gif, OutputConfigurations configurations);

}
