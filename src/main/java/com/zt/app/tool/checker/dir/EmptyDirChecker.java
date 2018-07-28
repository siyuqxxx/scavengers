package com.zt.app.tool.checker.dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyDirChecker extends ADirChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmptyDirChecker.class);

    @Override
    public String getName() {
        return "empty-dir-checker";
    }

    @Override
    public boolean check() {
        LOGGER.debug(String.format("%s: do nothing, and always return true.", this.getName()));
        return true;
    }
}
