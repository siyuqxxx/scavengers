package com.zt.app.tool.checker.dir.folder.decorater;

import com.zt.app.tool.checker.IDecorator;
import com.zt.app.tool.checker.dir.ADirChecker;
import com.zt.app.tool.checker.dir.DirCheckerFactory;
import com.zt.app.tool.checker.dir.IDirChecker;

import java.util.Objects;

public abstract class ADecFolderChecker extends ADirChecker implements IDecorator<IDirChecker> {
    IDirChecker c = DirCheckerFactory.create();

    @Override
    public void setComponent(IDirChecker c) {
        if (Objects.nonNull(c)) {
            this.c = c;
        }
    }
}
