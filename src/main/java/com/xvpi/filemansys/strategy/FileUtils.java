package com.xvpi.filemansys.strategy;


import java.util.Scanner;

public class FileUtils {
    private static Scanner scanner;

    public static void updateProgressBar(int progress) {
        // 更新进度条逻辑
        StringBuilder progressBar = new StringBuilder();
        progressBar.append("\r进度: [");
        int completed = progress / 10;
        for (int i = 0; i < 10; i++) {
            if (i < completed) {
                progressBar.append("■");
            } else {
                progressBar.append("□");
            }
        }
        progressBar.append("] ").append(progress).append("%");
        System.out.print(progressBar.toString());
    }
    public static String promptForOutputFileName(String prompt) {
        scanner = new Scanner(System.in);
        System.out.print("输入" + prompt + "：");
        return scanner.nextLine();
    }
}

