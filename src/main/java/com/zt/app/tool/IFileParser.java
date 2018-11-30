package com.zt.app.tool;

import com.zt.app.tool.common.Dir;

import java.io.File;
import java.util.List;

public interface IFileParser extends ILifeCycle {
    IFileParser setFile(File srcFileList);

    IFileParser setFile(String dir);

    List<Dir> getDirs();
}
