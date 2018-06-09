package com.zt.app.tool;

import com.zt.app.tool.common.Dir;

import java.util.List;

public interface IFileParser extends ILifeCycle {
    void setFile(String dir);

    List<Dir> getDirs();
}
