package com.zt.app.tool.checker.dir;

import com.zt.app.tool.checker.dir.file.FileChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class ADirChecker implements IDirChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileChecker.class);

    File f = null;

    public File getFile() {
        return f;
    }

    @Override
    public void setDir(String dir) {
        try {
            this.f = new File(dir);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void setDir(String parent, String child) {
        try {
            this.f = new File(parent, child);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void setDir(File parent, String child) {
        try {
            this.f = new File(parent, child);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
