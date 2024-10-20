package com.xvpi.filemansys.client;

import com.xvpi.filemansys.invoker.CommandInvoker;

public class Client {
    public static void main(String[] args) {
        CommandInvoker invoker = new CommandInvoker();
        invoker.start(); // 启动命令行交互
    }
}