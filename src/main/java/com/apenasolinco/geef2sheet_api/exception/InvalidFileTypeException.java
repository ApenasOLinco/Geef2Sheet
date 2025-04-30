package com.apenasolinco.geef2sheet_api.exception;

public class InvalidFileTypeException extends RuntimeException {
	private static final String message = """
		Invalid provided file type.
			Expected format: %s
			Provided format: %s
		""";

	private InvalidFileTypeException(String message) {
		super(message);
	}

	public InvalidFileTypeException(String expectedFormat, String providedFormat) {
		this(message.formatted(
			expectedFormat,
			providedFormat
		));
	}
}
