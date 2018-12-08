package com.zt.app.tool.common;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Dir {
    private String srcDir = "";
    private String targetDir = "";
    private File src = null;
    private List<File> targets = new LinkedList<>();
    private ERROR_CODES errorCode = ERROR_CODES.SUCCESS;

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

    public File getSrc() {
        return src;
    }

    public Dir setSrc(File src) {
        if (Objects.nonNull(src)) {
            this.src = src;
        }
        return this;
    }

    public Dir setSrc(String srcDir) {
        if (Objects.nonNull(srcDir)) {
            this.src = new File(srcDir);
        }
        return this;
    }

    public List<File> getTargets() {
        return targets;
    }

    public Dir addTarget(File target) {
        if (Objects.nonNull(target)) {
            this.targets.add(target);
        }
        return this;
    }

    public Dir addTarget(String targetDir) {
        if (Objects.nonNull(targetDir)) {
            this.targets.add(new File(targetDir));
        }
        return this;
    }

    public void addAllTargets(List<File> targets) {
        if (!targets.isEmpty()) {
            this.targets.addAll(targets);
        }
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
                ", src=" + src +
                ", targets=" + targets +
                ", errorCode=" + errorCode +
                '}';
    }
}
