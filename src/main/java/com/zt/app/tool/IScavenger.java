package com.zt.app.tool;

import java.util.List;

public interface IScavenger {
    String getName();

    boolean isNameMatch(String name);

    void setDirs(List<Dir> dirs);

    void setResultFolder(String folder);

    ERROR_CODES execute();
}
