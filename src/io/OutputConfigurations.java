package io;

public final class OutputConfigurations {
	private int numberOfColumns = 3, hGap = 0, vGap = 0;
	private String fileFormat = "png";
	
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	
	public int gethGap() {
		return hGap;
	}
	
	public int getvGap() {
		return vGap;
	}
	
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	
	public void sethGap(int hGap) {
		this.hGap = hGap;
	}
	
	public void setvGap(int vGap) {
		this.vGap = vGap;
	}

	public String getFileFormat() {
		return fileFormat;
	}
}
