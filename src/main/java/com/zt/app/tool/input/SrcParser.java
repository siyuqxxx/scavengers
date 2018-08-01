package com.zt.app.tool.input;

import com.zt.app.tool.checker.dir.DirCheckerFactory;
import com.zt.app.tool.common.ERROR_CODES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        if (super.getChecker().check()) {
            return ERROR_CODES.SUCCESS;
        } else {
            LOGGER.debug(String.format("execute %s failed", this.getName()));
            return ERROR_CODES.INVALID_SRC_FILE;
        }
    }
}
