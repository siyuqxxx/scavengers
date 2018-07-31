package com.zt.app.tool.checker.dir.folder.decorater;

import com.zt.app.tool.checker.IDecorator;
import com.zt.app.tool.checker.dir.DirCheckerFactory;
import com.zt.app.tool.checker.dir.IDirChecker;

import java.io.File;
import java.util.Objects;

public abstract class ADecFolderChecker implements IDirChecker, IDecorator<IDirChecker> {
    IDirChecker c = DirCheckerFactory.create();

    @Override
    public IDirChecker setComponent(IDirChecker c) {
        if (Objects.nonNull(c)) {
            this.c = c;
        }
        return this;
    }

    @Override
    public boolean check() {
        return this.c.check();
    }

    @Override
    public IDirChecker setDir(String dir) {
        return this.c.setDir(dir);
    }

    @Override
    public IDirChecker setDir(String parent, String child) {
        return this.c.setDir(parent, child);
    }

    @Override
    public IDirChecker setDir(File parent, String child) {
        return this.c.setDir(parent, child);
    }

    @Override
    public IDirChecker setDir(File f) {
        return this.c.setDir(f);
    }

    @Override
    public File getDir() {
        return this.c.getDir();
    }
}
