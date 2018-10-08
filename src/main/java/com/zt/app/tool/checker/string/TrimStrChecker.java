package com.zt.app.tool.checker.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class TrimStrChecker implements IStrChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrimStrChecker.class);

    @Override
    public String getName() {
        return "trim-str-checker";
    }

    @Override
    public boolean check(String s) {
        return Objects.nonNull(s) && !s.trim().isEmpty();
    }
}
