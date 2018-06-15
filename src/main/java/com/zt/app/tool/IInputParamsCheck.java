package com.zt.app.tool;

import com.zt.app.tool.common.InputParams;

public interface IInputParamsCheck extends ILifeCycle {
    IInputParamsCheck setParams(InputParams params);

    InputParams getParams();
}
