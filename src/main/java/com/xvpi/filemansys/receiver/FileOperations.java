package com.xvpi.filemansys.receiver;

import com.xvpi.filemansys.exception.FileManagementException;


public interface FileOperations {
    boolean createFile(String fileName);
    boolean deleteFile(String fileName) throws FileManagementException;
    String openFile(String fileName);
    void searchFiles(String searchTerm);
}
