package com.zt.app.tool.replace;

import com.zt.app.tool.ILifeCycle;
import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ReplacePattern;

import java.util.List;

public interface IReplacer extends ILifeCycle {
    IReplacer setDirs(List<Dir> dirs);

    DefaultReplacer setPatterns(List<ReplacePattern> patterns);

    DefaultReplacer setReplaceUnit(IReplaceUnit replaceUnit);

    List<Dir> getDirs();
}
