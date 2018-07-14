package com.zt.app.tool.checker.dir.file;

import com.zt.app.tool.checker.dir.ADirChecker;

import java.io.File;
import java.util.Objects;

public class FileChecker extends ADirChecker {
    @Override
    public File getFinalDir() {
        return super.getFile();
    }

    @Override
    public String getName() {
        return "file-checker";
    }

    @Override
    public boolean checker() {
        File f = getFinalDir();
        return Objects.nonNull(f) && f.exists() && f.isFile();
    }
}
