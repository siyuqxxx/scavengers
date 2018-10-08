package com.zt.app.tool.checker.dir.folder;

import com.zt.app.tool.checker.dir.ADirChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

public class FolderChecker extends ADirChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(FolderChecker.class);

    @Override
    public String getName() {
        return "folder-check";
    }

    @Override
    public boolean check() {
        File f = super.getDir();
        boolean isFolderValid = Objects.nonNull(f) && f.isDirectory();
        LOGGER.debug(String.format("%s: %s - %s", getName(), isFolderValid, f));
        return isFolderValid;
    }
}
