package com.zt.app.tool.checker.string;

import com.zt.app.tool.checker.string.decorator.StrTrimChecker;

public class StrCheckerFactory {
    public static IStrChecker createStrChecker() {
        return new StrChecker();
    }

    public static IStrChecker createStrTrimChecker() {
        return new StrTrimChecker();
    }
}
