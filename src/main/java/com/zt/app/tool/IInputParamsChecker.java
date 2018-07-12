package com.zt.app.tool;

import com.zt.app.tool.common.InputParams;

public interface IInputParamsChecker extends ILifeCycle {
    IInputParamsChecker setParams(InputParams params);

    InputParams getParams();
}
