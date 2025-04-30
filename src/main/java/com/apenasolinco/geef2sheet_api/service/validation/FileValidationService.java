package com.apenasolinco.geef2sheet_api.service.validation;

import com.apenasolinco.geef2sheet_api.exception.InvalidFileTypeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;

@Service
public class FileValidationService {

	public void validateContentType(MultipartFile file, MediaType acceptedType) {
		if (!IMAGE_GIF_VALUE.equals(file.getContentType())) {
			throw new InvalidFileTypeException(acceptedType.toString(), IMAGE_GIF_VALUE);
		}
	}

	public void validateFileExtension(MultipartFile file, String acceptedExtension) {
		var fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

		if (
			fileExtension == null ||
				!fileExtension.equals(acceptedExtension)
		) {
			throw new InvalidFileTypeException(acceptedExtension, fileExtension);
		}
	}

}
