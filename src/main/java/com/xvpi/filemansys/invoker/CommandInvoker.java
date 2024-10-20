package com.xvpi.filemansys.invoker;

import com.xvpi.filemansys.command.*;
import com.xvpi.filemansys.receiver.EncryptionManager;
import com.xvpi.filemansys.receiver.FileManager;

import java.util.Scanner;

public class CommandInvoker {
    private FileManager fileManager;
    private Scanner scanner;

    public CommandInvoker() {
        this.fileManager = new FileManager(); // 默认工作文件夹
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            showCurrentDirectory(); // 显示当前工作文件夹路径
            showMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    setWorkingDirectory();
                    break;
                case "2":
                    createFile();
                    break;
                case "3":
                    deleteFile();
                    break;
                case "4":
                    listFiles();
                    break;
                case "5":
                    createDirectory();
                    break;
                case "6":
                    deleteDirectory();
                    break;
                case "7":
                    copyFile();
                    break;
                case "8":
                    copyDirectory();
                    break;
                case "9":
                    encryptFile();
                    break;
                case "10":
                    decryptFile();
                    break;
                case "11":
                    compressDirectory();
                    break;
                case "12":
                    decompressFile();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("无效选项，请重试。");
            }
        }
    }

    private void showCurrentDirectory() {
        System.out.println("\n当前工作文件夹路径: " + fileManager.getCurrentDirectory());
    }

    private void showMenu() {
        System.out.println("\n==== 文件管理系统 ====");
        System.out.println("1. 设置当前工作文件夹");
        System.out.println("2. 创建文件");
        System.out.println("3. 删除文件");
        System.out.println("4. 罗列当前文件夹内容");
        System.out.println("5. 创建文件夹");
        System.out.println("6. 删除文件夹");
        System.out.println("7. 拷贝文件");
        System.out.println("8. 拷贝文件夹");
        System.out.println("9. 加密文件");
        System.out.println("10. 解密文件");
        System.out.println("11. 压缩文件夹");
        System.out.println("12. 解压文件");
        System.out.println("0. 退出");
        System.out.print("请选择操作：");
    }

    private void setWorkingDirectory() {
        Command command = new SetWorkingDirectoryCommand(fileManager);
        command.execute();
    }

    private void createFile() {
        System.out.print("输入文件名：");
        String fileName = scanner.nextLine();
        Command command = new CreateFileCommand(fileManager, fileName);
        command.execute();
    }

    private void deleteFile() {
        System.out.print("输入要删除的文件名：");
        String fileName = scanner.nextLine();
        Command command = new DeleteFileCommand(fileManager, fileName);
        command.execute();
    }

    private void listFiles() {
        Command command = new ListFilesCommand(fileManager);
        command.execute();
    }

    private void createDirectory() {
        System.out.print("输入文件夹名：");
        String dirName = scanner.nextLine();
        Command command = new CreateDirectoryCommand(fileManager, dirName);
        command.execute();
    }

    private void deleteDirectory() {
        System.out.print("输入要删除的文件夹名：");
        String dirName = scanner.nextLine();
        Command command = new DeleteDirectoryCommand(fileManager, dirName);
        command.execute();
    }

    private void copyFile() {
        System.out.print("输入源文件名：");
        String sourceFileName = scanner.nextLine();
        System.out.print("输入目标文件名：");
        String targetFileName = scanner.nextLine();
        Command command = new CopyFileCommand(fileManager, sourceFileName, targetFileName);
        command.execute();
    }

    private void copyDirectory() {
        System.out.print("输入源文件夹名：");
        String sourceDirName = scanner.nextLine();
        System.out.print("输入目标文件夹名：");
        String targetDirName = scanner.nextLine();
        Command command = new CopyDirectoryCommand(fileManager, sourceDirName, targetDirName);
        command.execute();
    }

    private void encryptFile() {
        System.out.print("输入要加密的文件名：");
        String fileName = scanner.nextLine();
        Command command = new EncryptFileCommand(new EncryptionManager(), fileName);
        command.execute();
    }

    private void decryptFile() {
        System.out.print("输入要解密的文件名：");
        String fileName = scanner.nextLine();
        Command command = new DecryptFileCommand(new EncryptionManager(), fileName);
        command.execute();
    }

    private void compressDirectory() {
        System.out.print("输入要压缩的文件夹名：");
        String dirName = scanner.nextLine();
        System.out.print("输入压缩文件名：");
        String zipFileName = scanner.nextLine();
        Command command = new CompressCommand(fileManager, dirName, zipFileName);
        command.execute();
    }

    private void decompressFile() {
        System.out.print("输入要解压的文件名：");
        String zipFileName = scanner.nextLine();
        System.out.print("输入目标文件夹名：");
        String destinationDirName = scanner.nextLine();
        Command command = new DecompressCommand(fileManager, zipFileName, destinationDirName);
        command.execute();
    }
}
