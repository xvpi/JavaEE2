package com.xvpi.filemansys.command;
import com.xvpi.filemansys.receiver.FileManager;
public class DeleteDirectoryCommand implements Command {
    private FileManager fileManager;
    private String dirName;

    public DeleteDirectoryCommand(FileManager fileManager, String dirName) {
        this.fileManager = fileManager;
        this.dirName = dirName;
    }

    @Override
    public void execute() {
        if (fileManager.deleteDirectory(dirName)) {
            System.out.println("文件夹删除成功: " + dirName);
        } else {
            System.out.println("文件夹删除失败: " + dirName);
        }
    }
}