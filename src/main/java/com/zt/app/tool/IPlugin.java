package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;

public interface IPlugin {
    String getName();

    boolean isMatch(String pattern);

    ERROR_CODES execute();
}