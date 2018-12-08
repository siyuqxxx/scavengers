package com.zt.app.tool.replace;

import com.zt.app.tool.checker.dir.DirCheckerFactory;
import com.zt.app.tool.checker.string.StrCheckerFactory;
import com.zt.app.tool.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DefaultReplacer implements IReplacer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReplacer.class);
    private InputParams params = null;
    private List<Dir> dirs = new LinkedList<>();
    private IReplaceUnit replaceUnit = new DefaultReplaceUtil();
    private List<ReplacePattern> patterns = new LinkedList<>();

    @Override
    public DefaultReplacer setParams(InputParams params) {
        if (Objects.nonNull(params)) {
            this.params = params;
        }
        return this;
    }

    @Override
    public DefaultReplacer setPatterns(List<ReplacePattern> patterns) {
        if (Objects.nonNull(patterns) && !patterns.isEmpty()) {
            this.patterns.clear();
            this.patterns.addAll(patterns);
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
            // 此时，暂时不验证src路径下的源码以及target路径下的二进制文件合法性。
            if (!StrCheckerFactory.createStrTrimChecker().check(srcDir)) {
                dir.setErrorCode(ERROR_CODES.INVALID_SRC_FILE);
            }

            File src = new File(this.params.getProject(), dir.getSrcDir());
            if (DirCheckerFactory.create(DirCheckerFactory.DIR_CHECKER.FILE).check(src)) {
                dir.setSrc(src);
            } else {
                dir.setErrorCode(ERROR_CODES.INVALID_SRC_FILE);
            }
        }
        return ERROR_CODES.SUCCESS;
    }

    @Override
    public String toReport() {
        Long srcDirInvalidCount = dirs.stream().filter(d -> d.getErrorCode() == ERROR_CODES.INVALID_SRC_FILE).count();
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
