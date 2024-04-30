import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import java.awt.image.BufferedImage;

public class OutputManager {
    private static final int IMAGES_PER_LINE = 3;

    void process(HashMap<Image[], String> gifs, ImageReader reader) {
        try {
            for(var e : gifs.entrySet()) {
                File outputFile = new File(String.format("%s/%s Frames.png", App.getOutputFolder().getPath(), e.getValue()));
                writeImagesToFile(e.getKey(), outputFile, reader);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void writeImagesToFile(Image[] imgs, File outputFile, ImageReader reader) throws IOException {
        outputFile.createNewFile();
        
        int imageWidth = imgs[0].getWidth(null);
        int imageHeight = imgs[0].getHeight(null);
        int lineWidth = imageWidth * IMAGES_PER_LINE;
        int lineHeight = imageHeight * (int)Math.ceil((double)imgs.length / IMAGES_PER_LINE);
        
        BufferedImage outputImage = new BufferedImage(lineWidth, lineHeight,BufferedImage.TYPE_INT_ARGB);

        for(int i = 0; i < imgs.length; i++) {
            Image img = imgs[i];

            int x = (i % IMAGES_PER_LINE) * imageWidth;
            int y = (i / IMAGES_PER_LINE) * imageHeight;

            outputImage.getGraphics().drawImage(img, x, y, null);
        }

        ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
        ImageOutputStream output = ImageIO.createImageOutputStream(outputFile);
        writer.setOutput(output);
        writer.write(outputImage);
        output.close();
        writer.dispose();
    }
}
