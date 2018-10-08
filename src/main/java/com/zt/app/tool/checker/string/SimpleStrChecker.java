package com.zt.app.tool.checker.string;

import java.util.Objects;

public class SimpleStrChecker implements IStrChecker {
    @Override
    public String getName() {
        return "Simple-str-checker";
    }

    @Override
    public boolean check(String s) {
        return Objects.nonNull(s) && !s.isEmpty();
    }
}
