package io;

public final class OutputConfigurations {
	private static int 
		numberOfColumns = 15,
		hGap = 0,
		vGap = 0;
	
	private static String fileFormat = "png";
	
	public static int getNumberOfColumns() {
		return numberOfColumns;
	}
	
	public static int gethGap() {
		return hGap;
	}
	
	public static int getvGap() {
		return vGap;
	}
	
	public static void setNumberOfColumns(int numberOfColumns) {
		OutputConfigurations.numberOfColumns = numberOfColumns;
	}
	
	public static void sethGap(int hGap) {
		OutputConfigurations.hGap = hGap;
	}
	
	public static void setvGap(int vGap) {
		OutputConfigurations.vGap = vGap;
	}

	public static String getFileFormat() {
		return fileFormat;
	}
}
