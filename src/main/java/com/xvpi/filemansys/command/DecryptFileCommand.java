package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.EncryptionManager;

public class DecryptFileCommand implements Command {
    private EncryptionManager encryptionManager;
    private String fileName;

    public DecryptFileCommand(EncryptionManager encryptionManager, String fileName) {
        this.encryptionManager = encryptionManager;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        encryptionManager.decryptFile(fileName);
    }
}