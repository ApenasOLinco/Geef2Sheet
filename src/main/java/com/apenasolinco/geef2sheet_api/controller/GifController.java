package com.apenasolinco.geef2sheet_api.controller;

import com.apenasolinco.geef2sheet_api.model.OutputConfigurations;
import com.apenasolinco.geef2sheet_api.service.GifService;
import com.apenasolinco.geef2sheet_api.service.validation.FileValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gif")
public class GifController {

	@Autowired
	private GifService gifService;

	@Autowired
	private FileValidationService fileValidationService;

	@PostMapping(
		value = "/convert",
		produces = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE }
	)
	public ResponseEntity<byte[]> gifToImage(
		@RequestParam("gif") MultipartFile file,
		OutputConfigurations outputConfigurations
	) {
		fileValidationService.validateContentType(file, MediaType.IMAGE_GIF);
		fileValidationService.validateFileExtension(file, "gif");
		var sheet = gifService.gifToSheet(file, outputConfigurations);

		return ResponseEntity.ok(sheet);
	}

	@GetMapping
	public ResponseEntity<String> testAPI() {
		return ResponseEntity.ok().body("TEST");
	}
}