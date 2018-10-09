package com.zt.app.tool.checker.dir;

import java.io.File;

public class EmptyDirChecker extends ADirChecker {

    @Override
    public String getName() {
        return "empty dir checker";
    }

    @Override
    public boolean check(File obj) {
        return true;
    }
}
