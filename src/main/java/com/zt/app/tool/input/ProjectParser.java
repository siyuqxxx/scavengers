package com.zt.app.tool.input;

import com.zt.app.tool.checker.dir.DirCheckerFactory;
import com.zt.app.tool.common.ERROR_CODES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ProjectParser extends AInputParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectParser.class);

    public ProjectParser() {
        super.setChecker(DirCheckerFactory.create(DirCheckerFactory.DIR_CHECKER.MVN_WEB_PROJECT));
    }

    @Override
    public String getName() {
        return "project-parser";
    }

    @Override
    public ERROR_CODES check() {

        File mvnProjectFolder = new File(super.getInputString());
        if (super.getChecker().check(mvnProjectFolder)) {
            super.getResultHolder().setSrc(mvnProjectFolder);
            return ERROR_CODES.SUCCESS;
        }
        LOGGER.debug(String.format("invalid project folder input dir。 %s", super.getInputString()));

        mvnProjectFolder = super.getResultHolder().getSrc().getParentFile();
        if (super.getChecker().check(mvnProjectFolder)) {
            super.getResultHolder().setSrc(mvnProjectFolder);
            return ERROR_CODES.SUCCESS;
        }

        mvnProjectFolder = new File("");
        if (super.getChecker().check(mvnProjectFolder)) {
            super.getResultHolder().setSrc(mvnProjectFolder);
            return ERROR_CODES.SUCCESS;
        }

        LOGGER.debug(String.format("execute %s failed", this.getName()));
        return ERROR_CODES.INVALID_PROJECT_DIR;
    }

}
