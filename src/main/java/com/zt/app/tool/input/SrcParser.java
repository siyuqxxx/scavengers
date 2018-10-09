package com.zt.app.tool.input;

import com.zt.app.tool.checker.dir.DirCheckerFactory;
import com.zt.app.tool.common.ERROR_CODES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class SrcParser extends AInputParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(SrcParser.class);

    public SrcParser() {
        super.setChecker(DirCheckerFactory.create(DirCheckerFactory.DIR_CHECKER.FILE));
    }

    @Override
    public String getName() {
        return "src-parser";
    }

    @Override
    public ERROR_CODES check() {
        File srcFile = new File(super.getInputString());
        if (super.getChecker().check(srcFile)) {
            super.getResultHolder().setSrc(srcFile);
            return ERROR_CODES.SUCCESS;
        }

        srcFile = new File("");
        if (super.getChecker().check(srcFile)) {
            super.getResultHolder().setSrc(srcFile);
            return ERROR_CODES.SUCCESS;
        }

        LOGGER.debug(String.format("execute %s failed", this.getName()));
        return ERROR_CODES.INVALID_SRC_FILE;
    }
}
