package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;

public interface ILifeCycle extends IPlugin {
    ERROR_CODES check();

    String toReport();
}
