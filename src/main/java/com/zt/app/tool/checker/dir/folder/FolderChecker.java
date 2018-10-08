package com.zt.app.tool.checker.dir.folder;

import com.zt.app.tool.checker.dir.ADirChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FolderChecker extends ADirChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(FolderChecker.class);

    @Override
    public String getName() {
        return "folder-checker";
    }

    @Override
    public boolean check(File folder) {
        boolean isFolderValid = super.check(folder) && folder.isDirectory();
        LOGGER.debug(String.format("%s: %s - %s", getName(), isFolderValid, folder));
        return isFolderValid;
    }
}
