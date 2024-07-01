package io;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class InputManager {
    private static final File INPUT_FILE = main.App.getInputFolder();
    
    private final OutputManager outputManager = new OutputManager();

    private final File[] gifFiles;
    
    private final HashMap<Image[], String> bufferedGIFs;

    public InputManager() {
        gifFiles = INPUT_FILE.listFiles(f -> f.getName().endsWith(".gif"));
        assert(gifFiles != null) : "gifFiles variable is null!";
        bufferedGIFs = new HashMap<>(gifFiles.length);
    }
    
    public void process() {
        try {
            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            
            for(File gif : gifFiles) {
                bufferedGIFs.put(bufferGIF(gif, reader), gif.getName().replace(".gif", ""));;
                outputManager.process(bufferedGIFs, reader);
            }
            reader.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Image[] bufferGIF(File gif, ImageReader reader) throws IOException {
        ImageInputStream input = ImageIO.createImageInputStream(gif);
        reader.setInput(input, false);

        int gifLength = reader.getNumImages(true);
        Image[] images = new Image[gifLength];
        
        for(int i = 0; i < gifLength; i++) {
            BufferedImage image = reader.read(i);
            images[i] = image;
            System.out.println(image.getWidth() + "x" + image.getHeight());
        }
        
        return images;
    }
}