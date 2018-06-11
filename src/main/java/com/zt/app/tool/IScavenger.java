package com.zt.app.tool;

import com.zt.app.tool.common.Dir;

import java.util.List;

public interface IScavenger extends ILifeCycle {
    void setDirs(List<Dir> dirs);

    void setResultFolder(String folder);

    void setPrefixPath(String prefixFolder);
}
