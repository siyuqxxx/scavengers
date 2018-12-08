package com.zt.app.tool.replace;

import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import com.zt.app.tool.common.ReplacePattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
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
        this.dir.addTarget(baseTarget);
        String[] filePattern = baseTarget.getName().split("\\.");
        if (filePattern.length == 2) {
            String fileName = filePattern[0];
            String fileSuffix = filePattern[1];
            File[] files = baseTarget.getParentFile().listFiles();
            if (Objects.nonNull(files)) {
                List<File> targets = Arrays.stream(files).filter(e -> e.getName().startsWith(fileName + "$")).filter(e -> e.getName().endsWith("." + fileSuffix)).collect(Collectors.toList());
                this.dir.addAllTargets(targets);
                LOGGER.debug("find multiple matching class files" + targets);
            }
        } else {
            LOGGER.error("invalid target name: " + baseTarget.getName());
            return ERROR_CODES.TARGET_DIR_INVALID;
        }
        return ERROR_CODES.SUCCESS;
    }
}
