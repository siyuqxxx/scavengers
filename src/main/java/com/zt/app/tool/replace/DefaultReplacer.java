package com.zt.app.tool.replace;

import com.zt.app.tool.DefaultDirChecker;
import com.zt.app.tool.IDirChecker;
import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.ReplacePattern;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DefaultReplacer implements IReplacer {
    private List<Dir> dirs = new LinkedList<>();
    private IDirChecker checker = new DefaultDirChecker();
    private IReplaceUnit replaceUnit = new DefaultReplaceUtil();
    private List<ReplacePattern> patterns = new LinkedList<>();

    @Override
    public DefaultReplacer setPatterns(List<ReplacePattern> patterns) {
        this.patterns.clear();
        this.patterns.addAll(patterns);
        return this;
    }

    @Override
    public DefaultReplacer setReplaceUnit(IReplaceUnit replaceUnit) {
        if (Objects.nonNull(replaceUnit)) {
            this.replaceUnit = replaceUnit;
        }
        return this;
    }

    @Override
    public String getName() {
        return "default-replacer";
    }

    @Override
    public ERROR_CODES execute() {
        check();
        for (Dir dir : dirs) {
            if (dir.getError_code() == ERROR_CODES.SUCCESS) {
                doReplace(dir);
            }
        }
        return ERROR_CODES.SUCCESS;
    }

    private void doReplace(Dir dir) {
        for (ReplacePattern pattern : this.patterns) {
            if (replaceUnit.setDir(dir).setPattern(pattern).isMatch()) {
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
            String srcDir = dir.getSrcDir();
            if (Objects.nonNull(srcDir) && !srcDir.trim().isEmpty()) {
                ERROR_CODES errorCode = checker.setDir(srcDir).execute();
                if (errorCode != ERROR_CODES.SUCCESS) {
                    dir.setError_code(ERROR_CODES.SRC_DIR_INVALID);
                }
            }
        }
        return ERROR_CODES.SUCCESS;
    }
}
