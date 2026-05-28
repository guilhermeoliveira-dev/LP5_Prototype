package org.example.log;

public enum LogType {
    SQL("SQL"),
    WARNING("WARNING"),
    ERROR("ERROR"),
    EXCEPTION("EXCEPTION"),
    SYSTEM("SYSTEM");

    private final String label;

    LogType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
