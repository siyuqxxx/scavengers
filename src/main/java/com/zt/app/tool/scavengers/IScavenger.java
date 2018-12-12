package com.zt.app.tool.scavengers;

import com.zt.app.tool.ILifeCycle;
import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.InputParams;

import java.util.List;

public interface IScavenger extends ILifeCycle {
    IScavenger setDirs(List<Dir> dirs);

    IScavenger setParams(InputParams params);
}
