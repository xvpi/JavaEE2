package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.EncryptionManager;

public class EncryptFileCommand implements Command {
    private EncryptionManager encryptionManager;
    private String inputFileName;
    private String outputFileName;

    public EncryptFileCommand(EncryptionManager encryptionManager, String inputFileName, String outputFileName) {
        this.encryptionManager = encryptionManager;
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    @Override
    public void execute() {
        if (encryptionManager.encryptFile(inputFileName, outputFileName)) {
            System.out.println("文件加密成功: " + outputFileName);
        } else {
            System.out.println("文件加密失败: " + inputFileName);
        }
    }
}

