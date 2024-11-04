package com.xvpi.filemansys.receiver;

public interface DirectoryOperations {
    boolean createDirectory(String dirName);
    boolean deleteDirectory(String dirName);
    void listFiles(String seq);
}