package com.xvpi.filemansys.receiver;

import com.xvpi.filemansys.utils.EncryptionUtils;

public class EncryptionManager {

    public void encryptFile(String fileName) {
        // 使用工具类加密文件
        EncryptionUtils.encrypt(fileName);
        System.out.println("File encrypted: " + fileName);
    }

    public void decryptFile(String fileName) {
        // 使用工具类解密文件
        EncryptionUtils.decrypt(fileName);
        System.out.println("File decrypted: " + fileName);
    }
}
