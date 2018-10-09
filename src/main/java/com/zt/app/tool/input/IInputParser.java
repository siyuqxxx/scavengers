package com.zt.app.tool.input;

import com.zt.app.tool.IPlugin;
import com.zt.app.tool.checker.dir.IDirChecker;
import com.zt.app.tool.common.InputParams;

public interface IInputParser extends IPlugin {
    public IInputParser setChecker(IDirChecker checker);

    public IInputParser setResultHolder(InputParams holder);

    public IInputParser setInputString(String inputString);
}
