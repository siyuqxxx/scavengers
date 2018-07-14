package com.zt.app.tool.input;

import com.zt.app.tool.checker.dir.IDirChecker;
import com.zt.app.tool.checker.dir.folder.FolderChecker;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public abstract class AInputChecker implements IInputChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(AInputChecker.class);

    private IDirChecker checker = new FolderChecker();

    private InputParams params = null;

    public IInputChecker setChecker(IDirChecker checker) {
        if (Objects.nonNull(checker)) {
            this.checker = checker;
        }
        return this;
    }

    public IInputChecker setParams(InputParams params) {
        if (Objects.nonNull(params)) {
            this.params = params;
        }
        return this;
    }

    IDirChecker getChecker() {
        return checker;
    }

    InputParams getParams() {
        return params;
    }

    @Override
    public ERROR_CODES execute() {
        if (Objects.nonNull(params) && Objects.nonNull(this.checker)) {
            return check();
        } else {
            LOGGER.error(String.format("%s do not set input params or checker", this.getName()));
            return ERROR_CODES.COMMON_ERROR;
        }
    }

    public abstract ERROR_CODES check();
}
