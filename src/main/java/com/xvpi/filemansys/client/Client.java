package com.xvpi.filemansys.client;

import com.xvpi.filemansys.command.*;
import com.xvpi.filemansys.receiver.FileManager;
import com.xvpi.filemansys.receiver.EncryptionManager;
import com.xvpi.filemansys.invoker.CommandInvoker;

public class Client {
    public static void main(String[] args) {
        // 创建接收者
        FileManager fileManager = new FileManager();
        EncryptionManager encryptionManager = new EncryptionManager();

        // 创建具体命令
        Command createFile = new CreateFileCommand(fileManager, "example.txt");
        Command deleteFile = new DeleteFileCommand(fileManager, "example.txt");
        Command copyFile = new CopyFileCommand(fileManager, "example.txt", "example_copy.txt");
        Command listFiles = new ListFilesCommand(fileManager);
        Command encryptFile = new EncryptFileCommand(encryptionManager, "example.txt");

        // 创建调用者
        CommandInvoker invoker = new CommandInvoker();

        // 执行命令
        invoker.setCommand(createFile);
        invoker.executeCommand();

        invoker.setCommand(listFiles);
        invoker.executeCommand();

        invoker.setCommand(copyFile);
        invoker.executeCommand();

        invoker.setCommand(encryptFile);
        invoker.executeCommand();

        invoker.setCommand(deleteFile);
        invoker.executeCommand();
    }
}
