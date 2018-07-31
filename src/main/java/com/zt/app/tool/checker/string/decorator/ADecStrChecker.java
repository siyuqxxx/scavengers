package com.zt.app.tool.checker.string.decorator;

import com.zt.app.tool.checker.IDecorator;
import com.zt.app.tool.checker.string.IStrChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public abstract class ADecStrChecker implements IStrChecker, IDecorator<IStrChecker> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ADecStrChecker.class);

    private IStrChecker strChecker = null;

    public IStrChecker getStrChecker() {
        return strChecker;
    }

    @Override
    public void setStr(String s) {
        if (Objects.nonNull(this.strChecker)) {
            strChecker.setStr(s);
        } else {
            LOGGER.debug("empty IStrChecker");
        }
    }

    @Override
    public String getStr() {
        return this.strChecker.getStr();
    }

    @Override
    public boolean check() {
        if (Objects.nonNull(this.strChecker)) {
            return strCheck();
        } else {
            LOGGER.debug("empty IStrChecker");
            return false;
        }
    }

    public abstract boolean strCheck();

    @Override
    public IStrChecker setComponent(IStrChecker checker) {
        if (Objects.nonNull(checker)) {
            this.strChecker = checker;
        }
        return this;
    }
}
