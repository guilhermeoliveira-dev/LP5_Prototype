package org.example.log;

public interface ILogger {


    void log(LogType type, String text);

    default String logFormat(LogType type, String text){
        return "["+type.getLabel()+"] "+text+"\n";
    }

}
