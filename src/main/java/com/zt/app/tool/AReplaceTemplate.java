package com.zt.app.tool;

import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import com.zt.app.tool.replace.IReplacer;

import java.util.List;
import java.util.Objects;

public abstract class AReplaceTemplate {
    private InputParams params;
    private IInputParamsChecker inputParamsChecker;
    private IFileParser reader;
    private IReplacer replacer;
    private IScavenger scavenger;

    public void setParams(InputParams params) {
        if (Objects.nonNull(params)) {
            this.params = params;
        }
    }

    public void setInputParamsChecker(IInputParamsChecker inputParamsChecker) {
        if (Objects.nonNull(inputParamsChecker)) {
            this.inputParamsChecker = inputParamsChecker;
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

            List<Dir> srcFiles = getScrFilesFrom(path);
            List<Dir> targetFiles = matchAndReplace(srcFiles);
            pickFrom(targetFiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_CODES.SUCCESS;
    }

    protected abstract void pickFrom(List<Dir> targetFiles);

    protected abstract List<Dir> matchAndReplace(List<Dir> srcFiles);

    protected abstract List<Dir> getScrFilesFrom(List<Dir> path);
}
