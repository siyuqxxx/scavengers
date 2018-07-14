package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import com.zt.app.tool.replace.IReplacer;

import java.util.Objects;

public class AReplaceTemplate {
    private InputParams params = null;
    private IInputParamsChecker checker = null;
    private IFileParser reader = null;
    private IReplacer replacer = null;
    private IScavenger scavenger = null;

    public void setParams(InputParams params) {
        if (Objects.nonNull(params)) {
            this.params = params;
        }
    }

    public void setChecker(IInputParamsChecker checker) {
        if (Objects.nonNull(checker)) {
            this.checker = checker;
        }
    }

    public void setReader(IFileParser reader) {
        if (Objects.nonNull(reader)) {
            this.reader = reader;
        }
    }

    public void setReplacer(IReplacer replacer) {
        if (Objects.nonNull(replacer)) {
            this.replacer = replacer;
        }
    }

    public void setScavenger(IScavenger scavenger) {
        if (Objects.nonNull(scavenger)) {
            this.scavenger = scavenger;
        }
    }

    public ERROR_CODES pickTargetFromSrc() {
        try {
            ERROR_CODES errorCodes = this.checker.setParams(null).execute();
            if (errorCodes != ERROR_CODES.SUCCESS) {
                return errorCodes;
            }

            errorCodes = this.reader.setFile(this.params.getSrc().toString()).execute();
            if (errorCodes != ERROR_CODES.SUCCESS) {
                return errorCodes;
            }

            errorCodes = this.replacer.setDirs(this.reader.getDirs()).execute();
            if (errorCodes != ERROR_CODES.SUCCESS) {
                return errorCodes;
            }

            errorCodes = this.scavenger.setDirs(this.replacer.getDirs()).setParams(this.params).execute();
            if (errorCodes != ERROR_CODES.SUCCESS) {
                return errorCodes;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_CODES.SUCCESS;
    }
}
