package com.zt.app.tool;

import com.zt.app.tool.common.Dir;

import java.util.List;

public interface IDirChecker extends ILifeCycle {
    void setDirs(List<Dir> dirs);
}
