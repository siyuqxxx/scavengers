package com.zt.app.tool.common;

public class Dir {
    String srcDir = "";
    String targetDir = "";
    ERROR_CODES error_code = ERROR_CODES.UNCHECK;

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

    public ERROR_CODES getError_code() {
        return error_code;
    }

    public Dir setError_code(ERROR_CODES error_code) {
        this.error_code = error_code;
        return this;
    }

    @Override
    public String toString() {
        return "Dir{" +
                "srcDir='" + srcDir + '\'' +
                ", targetDir='" + targetDir + '\'' +
                ", error_code=" + error_code +
                '}';
    }
}
