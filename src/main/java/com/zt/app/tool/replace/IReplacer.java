package com.zt.app.tool.replace;

import com.zt.app.tool.ILifeCycle;
import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ReplacePattern;

import java.util.List;

public interface IReplacer extends ILifeCycle {
    IReplacer setDirs(List<Dir> dirs);
    List<Dir> getDirs();

    public DefaultReplacer setReplaceUnit(IReplaceUnit replaceUnit);

    public DefaultReplacer setPatterns(List<ReplacePattern> patterns);
}
