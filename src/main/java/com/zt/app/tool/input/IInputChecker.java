package com.zt.app.tool.input;

import com.zt.app.tool.IPlugin;
import com.zt.app.tool.checker.dir.IDirChecker;
import com.zt.app.tool.common.InputParams;

public interface IInputChecker extends IPlugin {
    public IInputChecker setChecker(IDirChecker checker);

    public IInputChecker setParams(InputParams params);
}
