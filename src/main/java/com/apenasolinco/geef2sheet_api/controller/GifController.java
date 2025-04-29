package com.apenasolinco.geef2sheet_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gif")
public class GifController {
	@PostMapping("/convert")
	public ResponseEntity<String> imageToGif(
		@RequestParam("imagem") MultipartFile image
	) {
		return ResponseEntity.ok()
			.body("Recebida " + image.getOriginalFilename() + "\n");
	}

	@GetMapping
	public ResponseEntity<String> testAPI() {
		return ResponseEntity.ok().body("TEST");
	}
}
