package com.xvpi.filemansys.utils;

import java.io.File;

public class FileUtils {

    public static boolean isFileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }
}

