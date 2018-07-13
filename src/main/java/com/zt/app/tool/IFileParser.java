package com.zt.app.tool;

import com.zt.app.tool.common.Dir;

import java.util.List;

public interface IFileParser extends ILifeCycle {
    IFileParser setFile(String dir);

    List<Dir> getDirs();
}
