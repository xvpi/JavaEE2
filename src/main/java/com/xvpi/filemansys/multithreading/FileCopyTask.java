package com.xvpi.filemansys.multithreading;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;

public class FileCopyTask implements Runnable {
    private String sourceFile;
    private String destinationFile;

    public FileCopyTask(String sourceFile, String destinationFile) {
        this.sourceFile = sourceFile;
        this.destinationFile = destinationFile;
    }

    @Override
    public void run() {
        try {
            Path sourcePath = Paths.get(sourceFile);
            Path destinationPath = Paths.get(destinationFile);
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully: " + destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

