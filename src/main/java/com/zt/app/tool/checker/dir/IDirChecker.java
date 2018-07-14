package com.zt.app.tool.checker.dir;

import com.zt.app.tool.checker.IChecker;

import java.io.File;

public interface IDirChecker extends IChecker {
    public void setDir(String dir);

    public void setDir(String parent, String child);

    public void setDir(File parent, String child);

    public File getFinalDir();
}
