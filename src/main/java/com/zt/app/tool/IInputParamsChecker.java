package com.zt.app.tool;

import com.zt.app.tool.common.INPUT_PARAMS;
import com.zt.app.tool.common.InputParams;

import java.util.Map;

public interface IInputParamsChecker extends ILifeCycle {
    IInputParamsChecker setParams(Map<INPUT_PARAMS, String> params);

    InputParams getParams();
}
