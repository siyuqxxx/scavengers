package com.zt.app.tool;

import java.util.List;

public interface IDirChecker {
    String getName();

    boolean isNameMatch(String name);

    void setDirs(List<Dir> dirs);

    ERROR_CODES execute();
}
