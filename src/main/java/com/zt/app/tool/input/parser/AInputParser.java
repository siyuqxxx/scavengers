package com.zt.app.tool.input.parser;

import com.siyuqxxx.tool.checker.dir.DirCheckerFactory;
import com.siyuqxxx.tool.checker.dir.IDirChecker;
import com.siyuqxxx.tool.checker.string.StrCheckerFactory;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public abstract class AInputParser implements IInputParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(AInputParser.class);

    private IDirChecker checker = DirCheckerFactory.create();

    private InputParams resultHolder = null;

    private String inputString = "";

    public String getInputString() {
        return inputString;
    }

    @Override
    public IInputParser setInputString(String inputString) {
        if (StrCheckerFactory.createStrTrimChecker().check(inputString)) {
            this.inputString = inputString;
        }
        return this;
    }

    @Override
    public IInputParser setChecker(IDirChecker checker) {
        if (Objects.nonNull(checker)) {
            this.checker = checker;
        }
        return this;
    }

    public IDirChecker getChecker() {
        return checker;
    }

    @Override
    public final IInputParser setResultHolder(InputParams holder) {
        if (Objects.nonNull(holder)) {
            this.resultHolder = holder;
        }
        return this;
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
