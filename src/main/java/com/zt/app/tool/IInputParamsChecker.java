package com.zt.app.tool;

import com.zt.app.tool.common.InputParams;
import com.zt.app.tool.common.StrInputParams;

import java.util.List;

public interface IInputParamsChecker extends ILifeCycle {
    IInputParamsChecker setParams(List<StrInputParams> strParams);

    InputParams getParams();
}
