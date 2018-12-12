package com.zt.app.tool.read;

import com.zt.app.tool.ILifeCycle;
import com.zt.app.tool.common.Dir;

import java.io.File;
import java.util.List;

public interface ISrcListReader extends ILifeCycle {
    ISrcListReader setFile(File srcListFile);

    ISrcListReader setFile(String srcListDir);

    List<Dir> getDirs();
}
