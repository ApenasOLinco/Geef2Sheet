package com.apenasolinco.geef2sheet_api.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.InputStream;

@RestController
@RequestMapping("/gif")
public class GifController {

	@PostMapping("/convert")
	public ResponseEntity<Image> imageToGif(@RequestBody Image image) {
		return ResponseEntity.ok()
			.body(image);
	}
}
