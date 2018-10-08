package com.zt.app.tool.checker.string;

public class StrCheckerFactory {
    public static IStrChecker createStrChecker() {
        return new SimpleStrChecker();
    }

    public static IStrChecker createStrTrimChecker() {
        return new TrimStrChecker();
    }
}
