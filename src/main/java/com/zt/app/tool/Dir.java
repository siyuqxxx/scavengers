package com.zt.app.tool;

public class Dir {
    String srcDir = "";
    String targetDir = "";
    ERROR_CODES error_code = ERROR_CODES.UNCHECK;
    Exception e = null;

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

    public Exception getE() {
        return e;
    }

    public Dir setE(Exception e) {
        this.e = e;
        return this;
    }
}
