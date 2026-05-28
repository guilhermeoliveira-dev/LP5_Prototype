package org.example.log;

public class PrintLogger implements ILogger{

    private static final ILogger instance = new PrintLogger();

    public static ILogger get(){
        return instance;
    }

    protected PrintLogger(){}

    @Override
    public void log(LogType type, String text) {
        System.out.print(logFormat(type, text));
    }
}
