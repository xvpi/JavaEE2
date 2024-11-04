package com.xvpi.filemansys.receiver;

import java.io.*;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionManager {
    private static final String ALGORITHM = "AES";
    private static String key; // 将密钥作为类的实例变量

    public boolean encryptFile(String inputFileName, String outputFileName) {
        System.out.println("请输入加密密匙（1到16位）");
        Scanner scanner = new Scanner(System.in);
        key = normalizeKey(scanner.nextLine());
        return processFile(Cipher.ENCRYPT_MODE, inputFileName, outputFileName);
    }

    public boolean decryptFile(String inputFileName, String outputFileName) {
        System.out.println("请输入解密密匙");
        Scanner scanner = new Scanner(System.in);
        String secretKey = scanner.nextLine();
        secretKey = normalizeKey(secretKey); // 标准化解密密钥为16字节

        // 验证密钥
        if (!secretKey.equals(key)) {
            //System.out.println(secretKey);
            //System.out.println(key);
            System.out.println("解密失败：密钥不正确。");
            return false;
        }

        return processFile(Cipher.DECRYPT_MODE, inputFileName, outputFileName);
    }

    private String normalizeKey(String key) {
        StringBuilder normalizedKey = new StringBuilder(key);
        while (normalizedKey.length() < 16) {
            normalizedKey.append(normalizedKey.charAt(normalizedKey.length() % key.length()));
        }
        return normalizedKey.substring(0, 16); // 保证返回16字节
    }

    private boolean processFile(int cipherMode, String inputFile, String outputFile) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(cipherMode, keySpec);
            File dir = new File(inputFile);
            if (!dir.exists()) {
                System.out.println("源文件不存在: " + inputFile);
                return false;
            }
            // 获取源文件的父级目录
            String parentDir = dir.getParent();
            // 创建输出文件的完整路径
            String outputFileDir = parentDir + File.separator + outputFile;

            try (FileInputStream inputStream = new FileInputStream(inputFile);
                 FileOutputStream outputStream = new FileOutputStream(outputFileDir);
                 CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                System.out.println((cipherMode == Cipher.ENCRYPT_MODE ? "加密" : "解密") + "完成: " + inputFile);
                return true; // 操作成功，返回 true
            }
        } catch (Exception e) {
            System.out.println("处理文件时出错: " + e.getMessage());
            return false; // 操作失败，返回 false
        }
    }
}
