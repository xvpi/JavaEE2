package com.xvpi.filemansys.logger;

public class LogDecorator implements Logger {
    private Logger logger;

    public LogDecorator(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void log(String message) {
        // 记录额外的信息（如调用方法名、参数等）
        String decoratedMessage = "调用日志: " + message;
        logger.log(decoratedMessage);
    }

    public void logWithLevel(String level, String message) {
        String decoratedMessage = String.format("[%s] %s", level, message);
        logger.log(decoratedMessage);
    }
}