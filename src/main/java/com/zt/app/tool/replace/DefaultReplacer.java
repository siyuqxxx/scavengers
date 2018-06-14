package com.zt.app.tool.replace;

import com.zt.app.tool.DefaultDirChecker;
import com.zt.app.tool.IDirChecker;
import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.LogMsgFormat;
import com.zt.app.tool.common.ReplacePattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DefaultReplacer implements IReplacer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReplacer.class);
    private List<Dir> dirs = new LinkedList<>();
    private IDirChecker checker = new DefaultDirChecker();
    private IReplaceUnit replaceUnit = new DefaultReplaceUtil();
    private List<ReplacePattern> patterns = new LinkedList<>();

    @Override
    public DefaultReplacer setPatterns(List<ReplacePattern> patterns) {
        if (Objects.nonNull(patterns) && !patterns.isEmpty()) {
            this.patterns.clear();
            this.patterns.addAll(patterns);
        }
        return this;
    }

    @Override
    public DefaultReplacer setChecker(IDirChecker checker) {
        if (Objects.nonNull(checker)) {
            this.checker = checker;
        }
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
        LOGGER.info(String.format(LogMsgFormat.PLUGIN_START, getName()));
        check();
        for (Dir dir : dirs) {
            if (dir.getErrorCode() == ERROR_CODES.SUCCESS) {
                doReplace(dir);
            }
        }
        LOGGER.info(toReport());
        return ERROR_CODES.SUCCESS;
    }

    private void doReplace(Dir dir) {
        boolean isMatch = false;
        for (ReplacePattern pattern : this.patterns) {
            if (replaceUnit.setDir(dir).setPattern(pattern).isMatch()) {
                replaceUnit.execute();
                isMatch = true;
                break;
            }
        }
        if (!isMatch) {
            dir.setErrorCode(ERROR_CODES.NO_MATCHING_PATTERN);
            LOGGER.error("file not match any patterns: " + dir.getSrcDir());
        }
    }

    @Override
    public IReplacer setDirs(List<Dir> dirs) {
        if (Objects.nonNull(dirs) && !dirs.isEmpty()) {
            this.dirs.clear();
            this.dirs.addAll(dirs);
        }
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
                    dir.setErrorCode(ERROR_CODES.SRC_DIR_INVALID);
                }
            }
        }
        return ERROR_CODES.SUCCESS;
    }

    @Override
    public String toReport() {
        Long srcDirInvalidCount = dirs.stream().filter(d -> d.getErrorCode() == ERROR_CODES.SRC_DIR_INVALID).count();
        Long successCount = dirs.stream().filter(d -> d.getErrorCode() == ERROR_CODES.SUCCESS).count();
        Long srcDirNotMatchAnyPatternCount = dirs.stream().filter(d -> d.getErrorCode() == ERROR_CODES.NO_MATCHING_PATTERN).count();
        StringBuilder report = new StringBuilder();
        report.append(String.format("\nsrc dir invalid count:               %4d\n", srcDirInvalidCount));
        report.append(String.format("src dir not match any pattern count: %4d\n", srcDirNotMatchAnyPatternCount));
        report.append(String.format("success count:                       %4d\n", successCount));
        report.append("-----------------------------------------\n");
        report.append(String.format("total:                               %4d", dirs.size()));
        return report.toString();
    }
}
