package com.xvpi.filemansys.receiver;

import com.xvpi.filemansys.exception.FileManagementException;
import com.xvpi.filemansys.strategy.SortStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileManager extends AbstractFileManager {
    private Scanner scanner;
    @Override
    public boolean createFile(String fileName) {
        // 文件创建逻辑
        File file = new File(currentDirectory, fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("文件创建成功: " + file.getAbsolutePath());
                return true;
            } else {
                System.out.println("文件已存在: " + file.getAbsolutePath());
                return false;
            }
        } catch (IOException e) {
            System.out.println("文件创建失败: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteFile(String fileName) throws FileManagementException {
        // 文件删除逻辑
        File file = new File(currentDirectory, fileName);
        if (file.exists() && file.isFile()) {
            // 提示用户确认
            this.scanner = new Scanner(System.in);
            System.out.print("确认要删除文件 '" + fileName + "' 吗？(y/n): ");
            String confirmation = scanner.nextLine().trim(); // 读取用户输入并去除前后空格

            if (!confirmation.equalsIgnoreCase("y")) {
                System.out.println("删除操作已取消。");
                return false; // 用户取消操作
            }
            if (file.delete()) {
                System.out.println("文件删除成功: " + file.getAbsolutePath());
                return true;
            } else {
                throw new FileManagementException("文件删除失败: " + file.getAbsolutePath());
            }
        } else {
            System.out.println("文件不存在。");
            return false;
        }

    }

    @Override
    public boolean createDirectory(String dirName) {
        // 文件夹创建逻辑
        File dir = new File(currentDirectory, dirName);
        if (dir.mkdir()) {
            System.out.println("文件夹创建成功: " + dir.getAbsolutePath());
            return true;
        } else {
            System.out.println("文件夹创建失败或已存在。");
            return false;
        }
    }

    @Override
    public boolean deleteDirectory(String dirName) {
        // 文件夹删除逻辑
        this.scanner = new Scanner(System.in);
        File dir = new File(currentDirectory, dirName);
        if (dir.exists() && dir.isDirectory()) {
            // 提示用户确认
            System.out.print("确认要删除文件夹 '" + dirName + "' 吗？(y/n): ");
            String confirmation = scanner.nextLine();

            if (!confirmation.equalsIgnoreCase("y")) {
                System.out.println("删除操作已取消。");
                return false; // 用户取消操作
            }

            try {
                Files.walk(dir.toPath())
                        .map(Path::toFile)
                        .forEach(File::delete);
                System.out.println("文件夹删除成功: " + dir.getAbsolutePath());
                return true;
            } catch (IOException e) {
                System.out.println("文件夹删除失败: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("文件夹不存在。");
            return false;
        }
    }


    @Override
    public String openFile(String fileName) {
        // 文件读取逻辑
        File file = new File(currentDirectory, fileName);
        if (file.exists() && file.isFile()) {
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int lineNumber = 1; // 行号从1开始
                while ((line = reader.readLine()) != null) {
                    content.append(String.format("%-5d: %s%n", lineNumber, line)); // 添加行号
                    lineNumber++;
                }
                return content.toString(); // 返回文件内容
            } catch (IOException e) {
                System.out.println("文件读取失败: " + e.getMessage());
                return null;
            }
        } else {
            System.out.println("文件不存在。");
            return null;
        }
    }

    //查找文件
    @Override
    public void searchFiles(String searchTerm) {
        File dir = new File(currentDirectory);
        File[] files = dir.listFiles();

        if (files != null) {
            // 用于存储匹配的文件
            List<File> matchedFiles = new ArrayList<>();

            // 模糊查找文件
            for (File file : files) {
                if (file.getName().contains(searchTerm)) {
                    matchedFiles.add(file);
                }
            }

            // 如果找到匹配的文件
            if (!matchedFiles.isEmpty()) {
                System.out.printf("%-24s\t%-20s\t%-8s\t%-15s\n", "文件名", "最后修改时间", "类型", "大小"); // 表头
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for (File file : matchedFiles) {
                    String fileName = file.getName();
                    String lastModified = sdf.format(new Date(file.lastModified()));
                    String type = file.isDirectory() ? "directory" : "file";
                    long size = file.length();
                    System.out.printf("%-24s\t%-20s\t%-8s\t%d Bytes\n", fileName, lastModified, type, size);
                }
            } else {
                System.out.println("没有找到匹配的文件。");
            }
        } else {
            System.out.println("当前目录不存在或不是一个目录");
        }
    }


    private SortStrategy<File[]> sortStrategy;
    @Override
    // 列出当前文件夹内容，支持排序
    public void listFiles(String seq) {
        File dir = new File(currentDirectory);
        File[] files = dir.listFiles();
        SortStrategy<File[]> strategy = null;
        if (files != null) {
            // 选择排序策略
            switch (seq) {
                case "name":
                    sortStrategy = new SortStrategy.NameSortStrategy();
                    break;
                case "time":
                    sortStrategy = new SortStrategy.TimeSortStrategy();
                    break;
                case "byte":
                    sortStrategy = new SortStrategy.SizeSortStrategy();
                    break;
                default:
                    System.out.println("输入错误");
                    return;
            }
            // 使用选定的策略进行排序
            sortStrategy.sort(files);
            //打印文件信息
            System.out.printf("%-24s\t%-20s\t%-8s\t%-15s\n", "文件名", "最后修改时间", "类型", "大小"); // 表头
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (File file : files) {
                String fileName = file.getName();
                String lastModified = sdf.format(new Date(file.lastModified()));
                String type = file.isDirectory() ? "directory" : "file";
                long size = file.length();
                System.out.printf("%-24s\t%-20s\t%-8s\t%d Bytes\n", fileName, lastModified, type, size);
            }
        } else {
            System.out.println("当前目录不存在或不是一个目录");
        }
    }
}

