package com.zt.app.tool;

public interface ILogPrinter {
    String getName();

    boolean isNameMatch(String name);

    void resultFolder(String folder);

    ERROR_CODES execute();
}
