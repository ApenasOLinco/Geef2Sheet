package ui;

import java.awt.*;

/**
 * This class contains utility methods for dealing with Images.
 */
public class ImageUtil {
	/**
	 * Returns a new Image scaled to fit inside the provided canvas. The scaling
	 * maintains the image's aspect ratio. This algorithm is based on the one
	 * available at <a href=
	 * "https://codejava.net/java-se/graphics/drawing-an-image-with-automatic-scaling">the
	 * codejava website</a>.
	 * 
	 * @param image  the image to be scaled
	 * @param canvas the canvas to comport the image
	 */
	public static void drawImageScaledToCanvasRatio(Image image, Component canvas, Graphics g) {
		if (image == null || canvas == null || g == null)
			return;
		
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		double imageAspect = (double) imageHeight / imageWidth;
		
		int canvasWidth = canvas.getWidth();
		int canvasHeight = canvas.getHeight();
		double canvasAspect = (double) canvasHeight / canvasWidth;
		
		int x1 = 0; // Top-left Xpos
		int y1 = 0; // Top-left Ypos
		int x2 = 0; // Bottom-right Xpos
		int y2 = 0; // Bottom-right Ypos
		
		if (canvasAspect > imageAspect) {
			y1 = canvasHeight;
			// Keep image aspect ratio
			canvasHeight = (int) (canvasWidth * imageAspect);
			y1 = (y1 - canvasHeight) / 2;
		} else {
			x1 = canvasWidth;
			
			// Keep image aspect ratio
			canvasWidth = (int) (canvasHeight / imageAspect);
			x1 = (x1 - canvasWidth) / 2;
		}
		
		x2 = canvasWidth + x1;
		y2 = canvasHeight + y1;
		
		g.drawImage(image, x1, y1, x2, y2, 0, 0, imageWidth, imageHeight, null);
	}
}
