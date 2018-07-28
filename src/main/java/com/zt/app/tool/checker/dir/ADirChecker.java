package com.zt.app.tool.checker.dir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

public abstract class ADirChecker implements IDirChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(ADirChecker.class);

    File f = null;

    @Override
    public IDirChecker setDir(String dir) {
        try {
            this.f = new File(dir);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return this;
    }

    @Override
    public IDirChecker setDir(String parent, String child) {
        try {
            this.f = new File(parent, child);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return this;
    }

    @Override
    public IDirChecker setDir(File parent, String child) {
        try {
            this.f = new File(parent, child);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return this;
    }

    @Override
    public IDirChecker setDir(File f) {
        if (Objects.nonNull(f) && f.exists()) {
            this.f = f;
        }
        return this;
    }

    @Override
    public File getDir() {
        return Objects.nonNull(this.f) && this.f.exists() ? this.f.getAbsoluteFile() : null;
    }
}
