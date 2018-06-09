package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;

public interface ILifeCycle extends IPlugin {
    public ERROR_CODES check();
}
