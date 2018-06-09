package com.zt.app.tool;

import java.util.List;

public interface IScavenger extends ILifeCycle {
    void setDirs(List<Dir> dirs);

    void setResultFolder(String folder);
}
