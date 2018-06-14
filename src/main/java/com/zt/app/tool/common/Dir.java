package com.zt.app.tool.common;

public class Dir {
    String srcDir = "";
    String targetDir = "";
    ERROR_CODES errorCode = ERROR_CODES.SUCCESS;

    public String getSrcDir() {
        return srcDir;
    }

    public Dir setSrcDir(String srcDir) {
        this.srcDir = srcDir;
        return this;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public Dir setTargetDir(String targetDir) {
        this.targetDir = targetDir;
        return this;
    }

    public ERROR_CODES getErrorCode() {
        return errorCode;
    }

    public Dir setErrorCode(ERROR_CODES error_code) {
        this.errorCode = error_code;
        return this;
    }

    @Override
    public String toString() {
        return "Dir{" +
                "srcDir='" + srcDir + '\'' +
                ", targetDir='" + targetDir + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
