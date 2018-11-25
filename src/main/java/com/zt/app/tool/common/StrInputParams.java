package com.zt.app.tool.common;

import com.zt.app.tool.checker.string.StrCheckerFactory;

import java.util.Objects;

public class StrInputParams {
    private INPUT_PARAMS key = null;
    private String value = "";

    public INPUT_PARAMS getKey() {
        return key;
    }

    public StrInputParams setKey(INPUT_PARAMS key) {
        if (Objects.nonNull(key)) {
            this.key = key;
        }
        return this;
    }

    public String getValue() {
        return value;
    }

    public StrInputParams setValue(String value) {
        if (StrCheckerFactory.createStrTrimChecker().check(value)) {
            this.value = value;
        }
        return this;
    }
}
