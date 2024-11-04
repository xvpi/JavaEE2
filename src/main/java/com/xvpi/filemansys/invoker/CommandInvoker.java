package com.xvpi.filemansys.invoker;

import com.xvpi.filemansys.command.*;
import com.xvpi.filemansys.receiver.*;

import java.io.File;
import java.util.Scanner;

import static com.xvpi.filemansys.strategy.FileUtils.promptForOutputFileName;

public class CommandInvoker {
    private FileManager fileManager;
    private PathManager pathManager;
    private Scanner scanner;
    private String copiedFilePath;
    private String copiedFileName;

    public CommandInvoker() {
        this.fileManager = new FileManager();
        this.pathManager = new PathManager(fileManager); // 添加路径管理类
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        System.out.println(" ▄         ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄            ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄       ▄▄  " +
                "▄▄▄▄▄▄▄▄▄▄▄ \n" +
                "▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░▌          ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░▌     ▐░░▌▐░░░░░░░░░░░▌\n" +
                "▐░▌       ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░▌          ▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀█░▌▐░▌░▌   ▐░▐░▌▐░█▀▀▀▀▀▀▀▀▀ \n" +
                "▐░▌       ▐░▌▐░▌          ▐░▌          ▐░▌          ▐░▌       ▐░▌▐░▌▐░▌ ▐░▌▐░▌▐░▌          \n" +
                "▐░▌   ▄   ▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░▌          ▐░▌          ▐░▌       ▐░▌▐░▌ ▐░▐░▌ ▐░▌▐░█▄▄▄▄▄▄▄▄▄ \n" +
                "▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░░▌▐░▌          ▐░▌          ▐░▌       ▐░▌▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░░▌\n" +
                "▐░▌ ▐░▌░▌ ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░▌          ▐░▌          ▐░▌       ▐░▌▐░▌   ▀   ▐░▌▐░█▀▀▀▀▀▀▀▀▀ \n" +
                "▐░▌▐░▌ ▐░▌▐░▌▐░▌          ▐░▌          ▐░▌          ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌          \n" +
                "▐░▌░▌   ▐░▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄█░▌▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄▄▄ \n" +
                "▐░░▌     ▐░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌\n" +
                " ▀▀       ▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀  ▀▀▀▀▀▀▀▀▀▀▀ \n");

        System.out.println("欢迎使用命令行文件管理系统");
        while (running) {
            showCurrentDirectory();
            showMenu();
            String input = scanner.nextLine();
            String[] args = input.split(" ");
            String command = args[0];

            switch (command) {
                case "cd":
                    navigatePath(args);
                    break;
                case "chdir":
                    pathManager.showCurrentPath();
                    break;
                case "mkFile":
                    createFile(args);
                    break;
                case "delFile":
                    deleteFile(args);
                    break;
                case "mkDir":
                    createDirectory(args);
                    break;
                case "delDir":
                    deleteDirectory(args);
                    break;
                case "list":
                    listFiles(args);
                    break;
                case "open":
                    openFile(args);
                    break;
                case "copy":
                    copy(args);
                    break;
                case "paste":
                    paste(args);
                    break;
                case "encrypt":
                    encrypt(args);
                    break;
                case "decrypt":
                    decrypt(args);
                    break;
                case "compress":
                    compress(args);
                    break;
                case "decompress":
                    decompress(args);
                    break;
                case "find":
                    find(args);
                    break;
                case "exit":
                    running = false;
                    break;
                default:
                    System.out.println("无效命令，请重试。");
            }
            // 等待用户按Enter键继续
            System.out.println("按Enter继续...");
            scanner.nextLine(); // 等待用户输入
        }
    }

    private void showCurrentDirectory() {
        System.out.println("\n当前工作文件夹路径: " + pathManager.getCurrentDirectory());
    }

    private void showMenu() {
        System.out.println("\n============================= 文件管理系统 =============================");
        System.out.println("cd <路径> - 设置当前文件夹    cd .. - 返回上一层    cd ../.. - 返回上两层");
        System.out.println("chdir - 查看当前工作路径         list <name/time/byte> - 按序罗列文件夹内容");
        System.out.println("mkFile <文件名> - 创建文件       delFile <文件名> - 删除文件");
        System.out.println("mkDir <文件夹名> - 创建文件夹     delDir <文件夹名> - 删除文件夹");
        System.out.println("open <文件名> - 打开文本文件      find <关键词> - 查找文件");
        System.out.println("copy <文件/文件夹> - 拷贝        paste  - 粘贴文件/文件夹");
        System.out.println("encrypt <文件名> - 加密文件      decrypt <文件名> - 解密文件");
        System.out.println("compress <文件/文件夹> - 压缩    decompress <文件/文件夹> - 解压文件");
        System.out.println("exit - 退出");
        System.out.print("请输入命令：");
    }


    private void navigatePath(String[] args) {
        if (args.length == 2) {
            String path = args[1];

            // 处理 ".." 和 "../.." 的情况
            if ("..".equals(path)) {
                pathManager.moveUpOneLevel();
            } else if ("../..".equals(path)) {
                pathManager.moveUpTwoLevels();
            } else if (path.startsWith("F:\\")) { // 检查是否为绝对路径（以F:\为例）
                pathManager.changeDirectory(path);
            } else { // 处理相对路径
                pathManager.changeDirectory(path);
            }
        } else {
            System.out.println("请输入有效的路径。");
        }
    }


    // 复制文件或文件夹
    private void copy(String[] args) {
        if (args.length < 2) {
            System.out.println("请输入要拷贝的文件/文件夹名称。");
        } else {
            String sourceName = args[1];
            copiedFilePath = fileManager.getFilePath(sourceName);
            copiedFileName = sourceName;
            System.out.println("文件/文件夹已复制：" + copiedFileName);
        }
    }

    // 粘贴文件或文件夹
    private void paste(String[] args) {
        if (copiedFilePath == null || copiedFileName == null) {
            System.out.println("没有文件/文件夹被复制，无法粘贴。");
        } else {
            System.out.print("选择粘贴路径类型（1: 本地工作路径, 2: 用户自定义路径）：");
            String pathType = scanner.nextLine();

            String targetPath;
            if (pathType.equals("1")) {
                targetPath = pathManager.getCurrentDirectory();
            } else {
                System.out.print("输入粘贴后的自定义路径：");
                targetPath = scanner.nextLine();
            }

            System.out.print("输入粘贴后的文件/文件夹名称：");
            String newFileName = scanner.nextLine();

            System.out.print("选择执行方式（1: 前台, 2: 后台）：");
            String executionMode = scanner.nextLine();
            boolean isBackground = executionMode.equals("2");
            Command pasteCommand = new PasteCommand(new PasteManager(), copiedFilePath, targetPath + "/" + newFileName,
                    isBackground);
            pasteCommand.execute();
        }
    }


    // 创建文件
    private void createFile(String[] args) {
        if (args.length < 2) {
            System.out.println("请输入文件名。");
        } else {
            Command createFileCommand = new CreateFileCommand(fileManager, args[1]);
            createFileCommand.execute();
        }
    }

    // 删除文件
    private void deleteFile(String[] args) {
        if (args.length < 2) {
            System.out.println("请输入要删除的文件名。");
        } else {
            Command deleteFileCommand = new DeleteFileCommand(fileManager, args[1]);
            deleteFileCommand.execute();
        }
    }

    // 创建文件夹
    private void createDirectory(String[] args) {
        if (args.length < 2) {
            System.out.println("请输入文件夹名。");
        } else {
            Command createDirectoryCommand = new CreateDirectoryCommand(fileManager, args[1]);
            createDirectoryCommand.execute();
        }
    }

    // 删除文件夹
    private void deleteDirectory(String[] args) {
        if (args.length < 2) {
            System.out.println("请输入要删除的文件夹名。");
        } else {
            Command deleteDirectoryCommand = new DeleteDirectoryCommand(fileManager, args[1]);
            deleteDirectoryCommand.execute();
        }
    }

    // 列出当前文件夹内容
    private void listFiles(String[] args) {
        if(args.length < 2)
        {
            Command listCommand = new ListFilesCommand(fileManager,"name");
            listCommand.execute();
        }
        else {
            Command listCommand = new ListFilesCommand(fileManager, args[1]);
            listCommand.execute();
        }
    }

    // 查找
    private void find(String[] args) {
        if(args.length < 2)
        {
            System.out.println("请输入要查找的文件名。");
        }
        else {
            Command searchCommand = new SearchFileCommand(fileManager,args[1]);
            searchCommand.execute();
        }
    }

    // 打开文件
    private void openFile(String[] args) {
        if (args.length < 2) {
            System.out.println("请输入要打开的文件名。");
        } else {
            Command openFileCommand = new OpenFileCommand(fileManager, args[1]);
            openFileCommand.execute();
        }
    }



    //加密
    private void encrypt(String[] args) {
        if (args.length < 2) {
            System.out.println("请输入要加密的文件名。");
        } else {
            String inputFileName = pathManager.getCurrentDirectory() + "/" + args[1]; // 获取完整路径
            String outputFileName = promptForOutputFileName("加密后的文件名");
            Command encryptCommand = new EncryptFileCommand(new EncryptionManager(), inputFileName, outputFileName);
            encryptCommand.execute();
        }
    }

    //解密
    private void decrypt(String[] args) {
        if (args.length < 2) {
            System.out.println("请输入要解密的文件名。");
        } else {
            String inputFileName = pathManager.getCurrentDirectory() + "/" + args[1]; // 获取完整路径
            String outputFileName = promptForOutputFileName("解密后的文件名");
            Command decryptCommand = new DecryptFileCommand(new EncryptionManager(), inputFileName, outputFileName);
            decryptCommand.execute();
        }
    }

    // 压缩文件夹
    private void compress(String[] args) {
        if (args.length < 2) {
            System.out.println("请输入要压缩的文件/文件夹名。");
        } else {
            String sourcePath = pathManager.getCurrentDirectory() + "/" + args[1];
            String zipFileName = promptForOutputFileName("压缩文件名");

            Command compressCommand = new CompressCommand(new CompressionManager(), sourcePath, zipFileName);
            compressCommand.execute();
        }
    }


    // 解压文件
    private void decompress(String[] args) {
        if (args.length < 2) {
            System.out.println("请输入要解压的文件名。");
        } else {
            String sourceZipFile = pathManager.getCurrentDirectory() + File.separator + args[1];
            String destinationFileName = promptForOutputFileName("解压目标文件名"); // 直接要求输入文件名

            Command decompressCommand = new DecompressCommand(new CompressionManager(), sourceZipFile,
                    destinationFileName);
            decompressCommand.execute();
        }
    }



}
