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
        File f = getFinalDir();
        boolean isValid = Objects.nonNull(f) && f.exists() && f.isFile();
        LOGGER.debug(String.format("check is dir: %s - %s", isValid, f.toString()));
        return isValid;
    }
}
