package com.apenasolinco.geef2sheet_api.model;

public record OutputConfigurations(
	int numberOfColumns,
	int verticalGap,
	int horizontalGap,
	String outputFormat
) {

}
