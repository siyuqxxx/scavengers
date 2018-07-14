package com.zt.app.tool.checker.dir.folder;

import com.zt.app.tool.checker.dir.ADirChecker;

import java.io.File;
import java.util.Objects;

public class FolderChecker extends ADirChecker {
    @Override
    public File getFinalDir() {
        return super.getFile();
    }

    @Override
    public String getName() {
        return "folder-checker";
    }

    @Override
    public boolean checker() {
        File f = getFinalDir();
        return Objects.nonNull(f) && f.exists() && f.isDirectory();
    }
}
