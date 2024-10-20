package com.xvpi.filemansys.receiver;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class FileManager {

    public void createFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + fileName);
            } else {
                System.out.println("File already exists: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.delete()) {
            System.out.println("Deleted the file: " + fileName);
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    public void copyFile(String sourceFile, String destinationFile) {
        try {
            Path sourcePath = Paths.get(sourceFile);
            Path destinationPath = Paths.get(destinationFile);
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied to: " + destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listFiles() {
        File directory = new File(".");
        String[] files = directory.list();
        if (files != null) {
            for (String file : files) {
                System.out.println(file);
            }
        } else {
            System.out.println("Directory is empty.");
        }
    }
}

