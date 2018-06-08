package com.zt.app.tool;

import java.io.File;

public interface IFileParser {
    String getName();

    boolean isNameMatch(String name);

    void setFile(File file);

    ERROR_CODES execute();
}
