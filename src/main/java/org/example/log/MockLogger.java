package org.example.log;

import java.util.ArrayList;
import java.util.List;

public class MockLogger extends PrintLogger {

    private static final MockLogger instance = new MockLogger();

    public static MockLogger get(){
        return instance;
    }

    protected MockLogger(){
        super();
    }

    private final List<String> logs = new ArrayList<>();

    @Override
    public void log(LogType type, String text) {
        super.log(type, text);
        logs.add(logFormat(type, text));
    }

    public boolean containsLog(String text){
        return logs.stream().anyMatch(log -> log.contains(text));
    }

    public int getLogCount() {
        return logs.size();
    }

    public void clearLogs() {
        logs.clear();
    }
}