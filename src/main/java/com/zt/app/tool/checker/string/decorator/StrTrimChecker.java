package com.zt.app.tool.checker.string.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrTrimChecker extends ADecStrChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(StrTrimChecker.class);

    @Override
    public String getName() {
        return "str-trim-checker";
    }

    @Override
    public boolean StrCheck() {
        return super.getStrChecker().checker() && this.getStr().trim().isEmpty();
    }
}
