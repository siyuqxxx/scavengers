package com.zt.app.tool.replace;

import com.siyuqxxx.tool.checker.dir.DirCheckerFactory;
import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import com.zt.app.tool.common.ReplacePattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefaultReplaceUtil implements IReplaceUnit {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReplaceUtil.class);
    private Dir dir = new Dir();
    private ReplacePattern pattern = new ReplacePattern();
    private InputParams params = new InputParams();

    @Override
    public DefaultReplaceUtil setParams(InputParams params) {
        if (Objects.nonNull(params)) {
            this.params = params;
        }
        return this;
    }

    @Override
    public boolean isMatch() {
        for(String regex : pattern.getPatterns().keySet()) {
            if (! Pattern.compile(regex).matcher(dir.getSrcDir()).find()) {
                return false;
            }
        }
        LOGGER.debug(String.format("file match pattern: %s, %s", pattern.getName(), dir.getSrcDir()));
        return true;
    }

    @Override
    public void clean() {
        this.pattern = new ReplacePattern();
        this.dir = new Dir();
    }

    @Override
    public IReplaceUnit setDir(Dir dir) {
        this.dir = dir;
        return this;
    }

    @Override
    public IReplaceUnit setPattern(ReplacePattern pattern) {
        this.pattern = pattern;
        return this;
    }

    @Override
    public String getName() {
        return "default-replace-util";
    }

    @Override
    public ERROR_CODES execute() {
        String temp = dir.getSrcDir();
        for (Map.Entry<String, String> entry : pattern.getPatterns().entrySet()) {
            temp = temp.replaceFirst(entry.getKey(), entry.getValue());
        }
        dir.setTargetDir(temp);
        File baseTarget = new File(this.params.getTarget(), temp);
        String filename = baseTarget.getName();
        if (DirCheckerFactory.create(DirCheckerFactory.DIR_CHECKER.FILE).check(baseTarget)) {
            if (processClassFile(baseTarget)) {
                return ERROR_CODES.SUCCESS;
            }
            this.dir.addTarget(baseTarget);
        } else {
            LOGGER.error("target file not existed, or not a file: " + filename);
            return ERROR_CODES.TARGET_DIR_INVALID;
        }
        return ERROR_CODES.SUCCESS;
    }

    private boolean processClassFile(File baseTarget) {
        if (baseTarget.getName().endsWith(".class")) {
            File[] targets = baseTarget.getParentFile().listFiles(new JavaClassFilter().setFileFilterPattern(baseTarget));
            if (Objects.nonNull(targets)) {
                this.dir.addAllTargets(Arrays.stream(targets).collect(Collectors.toList()));
                return true;
            } else {
                this.dir.setErrorCode(ERROR_CODES.SRC_NOT_MATCH_ANY_TARGET);
                LOGGER.error("src not match any target." + baseTarget.toString());
                return false;
            }
        }
        return false;
    }
}
