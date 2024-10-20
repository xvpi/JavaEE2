package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.EncryptionManager;

public class EncryptFileCommand implements Command {
    private EncryptionManager encryptionManager;
    private String fileName;

    public EncryptFileCommand(EncryptionManager encryptionManager, String fileName) {
        this.encryptionManager = encryptionManager;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        encryptionManager.encryptFile(fileName);
    }
}
