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
        // todo 文件校验是否需要增加可读性校验
        if (super.getChecker().check(srcFile)) {
            super.getResultHolder().setSrc(srcFile);
            return ERROR_CODES.SUCCESS;
        }
        LOGGER.debug(String.format("invalid src list file input dir. %s", super.getInputString()));

        // todo 默认源码清单 （"src.txt"） 最好写入到配置中去，方便调整，不要写死
        srcFile = new File("", "src.txt");
        if (super.getChecker().check(srcFile)) {
            super.getResultHolder().setSrc(srcFile);
            return ERROR_CODES.SUCCESS;
        }

        LOGGER.debug(String.format("execute %s failed", this.getName()));
        return ERROR_CODES.INVALID_SRC_FILE;
    }
}
