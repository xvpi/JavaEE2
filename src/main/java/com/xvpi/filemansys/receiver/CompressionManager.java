package com.xvpi.filemansys.receiver;

import com.xvpi.filemansys.exception.FileManagementException;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CompressionManager {

    public boolean compress(String sourcePath, String zipFileName) {
        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists()) {
            System.out.println("源文件/文件夹不存在: " + sourcePath);
            return false;
        }

        // 获取源目录的父级目录
        String parentDir = sourceFile.getParent();
        // 创建压缩文件的完整路径
        String destZipFileName = parentDir + File.separator + zipFileName;

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destZipFileName))) {
            if (sourceFile.isDirectory()) {
                zipDirectory(sourceFile, sourceFile.getName(), zos);
            } else {
                zipFile(sourceFile, zos);
            }
            System.out.println("压缩完成: " + destZipFileName);
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // 输出详细异常信息
            System.out.println("压缩文件/文件夹时出错: " + e.getMessage());
            return false;
        }
    }

    // 压缩单个文件
    private void zipFile(File file, ZipOutputStream zos) throws IOException {
        zos.putNextEntry(new ZipEntry(file.getName()));
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
        }
        zos.closeEntry();
    }



    private void zipDirectory(File folder, String parentFolder, ZipOutputStream zos) throws IOException {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isHidden()) {
                    continue; // 跳过隐藏文件
                }
                if (file.isDirectory()) {
                    zipDirectory(file, parentFolder + File.separator + file.getName(), zos);
                } else {
                    zos.putNextEntry(new ZipEntry(parentFolder + File.separator + file.getName()));
                    try (FileInputStream fis = new FileInputStream(file)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = fis.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }
                    }
                    zos.closeEntry();
                }
            }
        }
    }

    public boolean decompress(String sourcePath, String destDirName) throws FileManagementException {
        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists()) {
            throw new FileManagementException("源压缩包不存在: " + sourcePath);
        }

        // 获取源目录的父级目录
        String parentDir = sourceFile.getParent();
        // 创建解压目标文件夹的完整路径
        String destUnZipDirName = parentDir + File.separator + destDirName;
        File destDir = new File(destUnZipDirName);

        // 如果目标目录不存在，则创建
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(sourceFile))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File newFile = new File(destUnZipDirName, zipEntry.getName());
                // 如果是目录，则创建
                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    // 创建文件的父目录
                    new File(newFile.getParent()).mkdirs();
                    // 写入文件内容
                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile))) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            bos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
            System.out.println("解压完成: " + destUnZipDirName);
            return true;
        } catch (IOException e) {
            System.out.println("解压文件时出错: " + e.getMessage());
            return false;
        }
    }
}
