package com.zt.app.tool.checker.string;

import com.zt.app.tool.checker.IChecker;

public interface IStrChecker extends IChecker {
    void setStr(String s);

    String getStr();
}
