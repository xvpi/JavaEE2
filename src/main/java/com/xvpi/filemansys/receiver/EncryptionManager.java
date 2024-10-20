package com.xvpi.filemansys.receiver;

import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionManager {
    private static final String ALGORITHM = "AES";
    private static final String KEY = "1234567890123456"; // 16字节密钥

    public void encryptFile(String fileName) {
        processFile(Cipher.ENCRYPT_MODE, fileName, fileName + ".enc");
    }

    public void decryptFile(String fileName) {
        processFile(Cipher.DECRYPT_MODE, fileName, fileName.replace(".enc", ""));
    }

    private void processFile(int cipherMode, String inputFile, String outputFile) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(cipherMode, keySpec);

            try (FileInputStream inputStream = new FileInputStream(inputFile);
                 FileOutputStream outputStream = new FileOutputStream(outputFile);
                 CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                System.out.println((cipherMode == Cipher.ENCRYPT_MODE ? "加密" : "解密") + "完成: " + inputFile);
            }
        } catch (Exception e) {
            System.out.println("处理文件时出错: " + e.getMessage());
        }
    }
}

