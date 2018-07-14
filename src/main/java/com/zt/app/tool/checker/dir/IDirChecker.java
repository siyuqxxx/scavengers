package com.zt.app.tool.checker.dir;

import com.zt.app.tool.checker.IChecker;

import java.io.File;

public interface IDirChecker extends IChecker {
    public IDirChecker setDir(String dir);

    public IDirChecker setDir(String parent, String child);

    public IDirChecker setDir(File parent, String child);

    public File getFinalDir();
}
