package com.zt.app.tool.replace;

import com.zt.app.tool.IPlugin;
import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ReplacePattern;

public interface IReplaceUnit extends IPlugin {

    boolean isMatch();
    void clean();

    IReplaceUnit setDir(Dir dir);

    IReplaceUnit setReplacePattern(ReplacePattern pattern);
}
