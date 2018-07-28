package com.zt.app.tool.checker.dir.file;

import com.zt.app.tool.checker.dir.ADirChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

public class FileChecker extends ADirChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileChecker.class);

    @Override
    public String getName() {
        return "file-check";
    }

    @Override
    public boolean check() {
        File f = getDir();
        boolean isValid = Objects.nonNull(f) && f.isFile();
        LOGGER.debug(String.format("%s: %s - %s", getName(), isValid, f));
        return isValid;
    }
}
