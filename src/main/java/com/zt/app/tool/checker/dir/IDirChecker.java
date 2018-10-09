package com.zt.app.tool.checker.dir;

import com.zt.app.tool.checker.IChecker;

import java.io.File;

public interface IDirChecker extends IChecker<File> {
    public boolean check(String dir);

    public boolean check(String parent, String child);

    public boolean check(File parent, String child);
}
