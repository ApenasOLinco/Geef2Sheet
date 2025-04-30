package com.apenasolinco.geef2sheet_api.controller;

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

	@PostMapping("/convert")
	public ResponseEntity<String> gifToImage(
		@RequestParam("gif")
		MultipartFile file
	) {
		fileValidationService.validateContentType(file, MediaType.IMAGE_GIF);
		fileValidationService.validateFileExtension(file, "gif");

		var image = gifService.gifToImage(file);

		return ResponseEntity.ok()
			.body("Recebida " + file.getOriginalFilename() + "\n");
	}

	@GetMapping
	public ResponseEntity<String> testAPI() {
		return ResponseEntity.ok().body("TEST");
	}
}
