package io;

import java.io.File;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import io.event.IONotifier.EventType;
import main.App;

public class InputProcessor {
    private final ArrayList<File> inputFiles = new ArrayList<>();

    public boolean addFiles(File @NotNull ... files) {
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