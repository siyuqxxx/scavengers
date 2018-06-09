package com.zt.app.tool;

public interface IPlugin {
    String getName();

    boolean isMatch(String pattern);

    ERROR_CODES execute();
}