package com.zt.app.tool.input;

import com.zt.app.tool.checker.dir.DirCheckerFactory;
import com.zt.app.tool.checker.dir.IDirChecker;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

public abstract class AInputChecker implements IInputChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(AInputChecker.class);

    private IDirChecker checker = DirCheckerFactory.create();

    private InputParams resultHolder = null;

    public final IInputChecker setChecker(IDirChecker checker) {
        if (Objects.nonNull(checker)) {
            LOGGER.debug("set input checker: " + checker.getName());
            this.checker = checker.setDir(this.checker.getDir());
        }
        return this;
    }

    public final IInputChecker setResultHolder(InputParams holder) {
        if (Objects.nonNull(holder)) {
            this.resultHolder = holder;
        }
        return this;
    }

    public final IInputChecker setDir(String dir) {
        this.checker.setDir(dir);
        return this;
    }

    public final IInputChecker setDir(String parent, String child) {
        this.checker.setDir(parent, child);
        return this;
    }

    public final IInputChecker setDir(File parent, String child) {
        this.checker.setDir(parent, child);
        return this;
    }

    public final IInputChecker setDir(File f) {
        this.checker.setDir(f);
        return this;
    }

    public final IDirChecker getChecker() {
        return this.checker;
    }

    public final InputParams getResultHolder() {
        return this.resultHolder;
    }

    @Override
    public final ERROR_CODES execute() {
        if (Objects.nonNull(resultHolder)) {
            return check();
        } else {
            LOGGER.error(String.format("%s do not set input resultHolder", this.getName()));
            return ERROR_CODES.EMPTY_RESULT_HOLDER;
        }
    }

    public abstract ERROR_CODES check();
}
