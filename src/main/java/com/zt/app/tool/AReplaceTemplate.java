package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.StrInputParams;
import com.zt.app.tool.replace.DefaultReplacer;
import com.zt.app.tool.replace.IReplacer;

import java.util.List;
import java.util.Objects;

public class AReplaceTemplate {
    private IInputParamsChecker checker = new DefaultInputParamChecker();
    private ISrcListReader reader = new DefaultSrcListReader();
    private IReplacer replacer = new DefaultReplacer();
    private IScavenger scavenger = new DefaultScavenger();

    public void setChecker(IInputParamsChecker checker) {
        if (Objects.nonNull(checker)) {
            this.checker = checker;
        }
    }

    public void setReader(ISrcListReader reader) {
        if (Objects.nonNull(reader)) {
            this.reader = reader;
        }
    }

    public AReplaceTemplate setReplacer(IReplacer replacer) {
        if (Objects.nonNull(replacer)) {
            this.replacer = replacer;
        }
        return this;
    }

    public void setScavenger(IScavenger scavenger) {
        if (Objects.nonNull(scavenger)) {
            this.scavenger = scavenger;
        }
    }

    public ERROR_CODES pickTargetFromSrc(List<StrInputParams> inputParams) {
        try {
            ERROR_CODES errorCodes = this.checker.setParams(inputParams).execute();
            if (errorCodes != ERROR_CODES.SUCCESS) {
                return errorCodes;
            }

            errorCodes = this.reader.setFile(this.checker.getParams().getSrc()).execute();
            if (errorCodes != ERROR_CODES.SUCCESS) {
                return errorCodes;
            }

            errorCodes = this.replacer.setDirs(this.reader.getDirs()).setParams(this.checker.getParams()).execute();
            if (errorCodes != ERROR_CODES.SUCCESS) {
                return errorCodes;
            }

            errorCodes = this.scavenger.setDirs(this.replacer.getDirs()).setParams(this.checker.getParams()).execute();
            if (errorCodes != ERROR_CODES.SUCCESS) {
                return errorCodes;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_CODES.SUCCESS;
    }
}
