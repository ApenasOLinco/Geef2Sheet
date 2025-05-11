package com.apenasolinco.geef2sheet_api.model;

import lombok.Data;

@Data
public class OutputConfigurations {
	private int numberOfColumns,
		verticalGap,
		horizontalGap;

	private String outputFormat;
}
