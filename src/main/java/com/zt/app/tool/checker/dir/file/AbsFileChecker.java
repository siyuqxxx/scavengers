package com.zt.app.tool.checker.dir.file;

import java.io.File;

public class AbsFileChecker extends FileChecker {
    @Override
    public String getName() {
        return "absolute-file-checker";
    }

    @Override
    public File getFinalDir() {
        return super.getFinalDir().getAbsoluteFile();
    }
}
