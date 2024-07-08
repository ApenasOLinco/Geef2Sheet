package io;

import io.event.IONotifier;
import io.event.IONotifier.EventType;
import main.App;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InputProcessor {
    private final ArrayList<File> inputFiles = new ArrayList<>();

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
            App.getIONotifier().notifyObservers(EventType.FILE_ADDED, files);
            return true;
        }

        return false;
    }
    
    public ArrayList<File> getInputFiles() {
        return inputFiles;
    }
}