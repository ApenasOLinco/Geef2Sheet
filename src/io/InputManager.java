package io;

import io.event.InputNotifier;
import io.event.InputNotifier.EventType;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InputManager {
    private final ArrayList<File> inputFiles = new ArrayList<>();

    private final InputNotifier inputNotifier = new InputNotifier();

    public boolean addFiles(File... files) {
        return addFiles(null, files);
    }

    public boolean addFiles(String[] formats, File @NotNull ... files) {
        int addedFiles = 0;

        for(var file : files) {
            if(!file.getName().endsWith("gif")) {
                System.out.printf("File %s is not a GIF file. Therefore, it will not be added.\n", file.getName());
                continue;
            }

            if(inputFiles.contains(file)) {
                System.out.printf("Tried to add a duplicate file into input: %s.\n", file.getName());
                continue;
            }

            if(inputFiles.add(file))
                addedFiles++;
        }


        if(addedFiles > 0) {
            inputNotifier.notifyObservers(EventType.FILE_ADDED, files);
            return true;
        }

        return false;
    }
    
	private Image[] bufferGIF(File gif, ImageReader reader) throws IOException {
        ImageInputStream input = ImageIO.createImageInputStream(gif);
        reader.setInput(input, false);

        int gifLength = reader.getNumImages(true);
        Image[] images = new Image[gifLength];
        
        for(int i = 0; i < gifLength; i++) {
            BufferedImage image = reader.read(i);
            images[i] = image;
            System.out.println(STR."\{image.getWidth()}x\{image.getHeight()}");
        }
        
        return images;
    }

    public ArrayList<File> getInputFiles() {
        return inputFiles;
    }

    public InputNotifier getInputNotifier() {
        return inputNotifier;
    }
}