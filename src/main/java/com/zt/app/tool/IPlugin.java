package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;

public interface IPlugin {
    String getName();

    ERROR_CODES execute();
}