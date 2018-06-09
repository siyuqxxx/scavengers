package com.zt.app.tool.replace;

import com.zt.app.tool.DefaultDirChecker;
import com.zt.app.tool.IDirChecker;
import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.DirHelper;
import com.zt.app.tool.common.ERROR_CODES;

import java.util.LinkedList;
import java.util.List;

public class DefaultReplacer implements IReplacer {
    private List<Dir> dirs = new LinkedList<>();
    private IDirChecker checker = new DefaultDirChecker();
    private List<IReplaceUnit> replaceUnits = new LinkedList<>();

    @Override
    public DefaultReplacer setReplaceUnits(List<IReplaceUnit> replaceUnits) {
        this.replaceUnits.clear();
        this.replaceUnits.addAll(replaceUnits);
        return this;
    }

    @Override
    public String getName() {
        return "default-replacer";
    }

    @Override
    public ERROR_CODES execute() {
        for (Dir dir : dirs) {
            doReplace(dir);
        }
        check();
        return ERROR_CODES.SUCCESS;
    }

    private void doReplace(Dir dir) {
        for (IReplaceUnit replaceUnit : this.replaceUnits){
            if (replaceUnit.setDir(dir).isMatch()) {
                replaceUnit.execute();
                break;
            }
        }
    }

    @Override
    public IReplacer setDirs(List<Dir> dirs) {
        this.dirs.clear();
        this.dirs.addAll(dirs);
        return this;
    }

    @Override
    public List<Dir> getDirs() {
        return this.dirs;
    }

    @Override
    public ERROR_CODES check() {
        for (Dir dir : this.dirs) {
            DirHelper.check(dir, this.checker);
        }
        return ERROR_CODES.SUCCESS;
    }
}
