package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.EncryptionManager;

public class DecryptFileCommand implements Command {
    private EncryptionManager encryptionManager;
    private String inputFileName;
    private String outputFileName;

    public DecryptFileCommand(EncryptionManager encryptionManager, String inputFileName, String outputFileName) {
        this.encryptionManager = encryptionManager;
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    @Override
    public void execute() {
        if (encryptionManager.decryptFile(inputFileName, outputFileName)) {
            System.out.println("文件解密成功: " + outputFileName);
        } else {
            System.out.println("文件解密失败: " + inputFileName);
        }
    }
}