package com.zt.app.tool.checker.dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

public abstract class ADirChecker implements IDirChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(ADirChecker.class);

    @Override
    public boolean check(String dir) {
        try {
            return check(new File(dir));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean check(String parent, String child) {
        try {
            return check(new File(parent, child));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean check(File parent, String child) {
        try {
            return check(new File(parent, child));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean check(File f) {
        return Objects.nonNull(f) && f.exists();
    }
}
