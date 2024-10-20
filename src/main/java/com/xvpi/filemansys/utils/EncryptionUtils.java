package com.xvpi.filemansys.utils;

import java.nio.file.*;
import java.io.IOException;

public class EncryptionUtils {

    public static void encrypt(String fileName) {
        // 简单加密操作，实际项目中应使用更复杂的加密算法
        Path path = Paths.get(fileName);
        try {
            byte[] data = Files.readAllBytes(path);
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (data[i] ^ 0xFF);  // 简单异或操作
            }
            Files.write(path, data);
            System.out.println("File encrypted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decrypt(String fileName) {
        // 解密操作，简单的异或解密
        encrypt(fileName); // 由于加密是异或操作，调用同样的方法可以解密
    }
}

