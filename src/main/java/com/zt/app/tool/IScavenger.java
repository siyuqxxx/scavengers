package com.zt.app.tool;

import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.InputParams;

import java.util.List;

public interface IScavenger extends ILifeCycle {
    void setDirs(List<Dir> dirs);

    void setParams(InputParams params);
}
