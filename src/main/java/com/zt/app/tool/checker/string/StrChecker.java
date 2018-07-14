package com.zt.app.tool.checker.string;

import java.util.Objects;

public class StrChecker implements IStrChecker {
    String s = null;

    @Override
    public String getName() {
        return "default-string-check";
    }

    @Override
    public String getStr() {
        return this.s;
    }

    @Override
    public boolean check() {
        return Objects.nonNull(this.s) && !s.isEmpty();
    }

    @Override
    public void setStr(String s) {
        this.s = s;
    }
}
