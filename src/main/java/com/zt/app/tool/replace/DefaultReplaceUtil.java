package com.zt.app.tool.replace;

import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.ReplacePattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultReplaceUtil implements IReplaceUnit {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultReplaceUtil.class);
    private Dir dir = new Dir();
    private ReplacePattern pattern = new ReplacePattern();
    
    @Override
    public boolean isMatch() {
        LOGGER.debug(String.format("dir: %s", dir.getSrcDir()));
        for(String regex : pattern.getPatterns().keySet()) {
            if (! Pattern.compile(regex).matcher(dir.getSrcDir()).find()) {
                LOGGER.debug(String.format("not match regex: %s ", regex));
                return false;
            }
        }
        LOGGER.debug(String.format("match pattern: %s ", pattern.getName()));
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
    public IReplaceUnit setReplacePattern(ReplacePattern pattern) {
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
            temp =  temp.replaceFirst(entry.getKey(), entry.getValue());
        }
        dir.setTargetDir(temp);
        return ERROR_CODES.SUCCESS;
    }
}
