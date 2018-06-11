package com.zt.app.tool;

import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DefaultScavenger implements IScavenger {
    private List<Dir> dirs = new LinkedList<>();

    private String resultFolder = "";

    private String prefixPath = "";

    @Override
    public void setDirs(List<Dir> dirs) {
        if (Objects.nonNull(dirs) && !dirs.isEmpty()) {
            this.dirs.clear();
            this.dirs.addAll(dirs);
        }
    }

    @Override
    public void setResultFolder(String folder) {
        this.resultFolder = folder;
    }

    @Override
    public void setPrefixPath(String path) {
        this.prefixPath = path;
    }

    @Override
    public ERROR_CODES check() {
        return null;
    }

    @Override
    public String toReport() {
        return null;
    }

    @Override
    public String getName() {
        return "default-scavenger";
    }

    @Override
    public ERROR_CODES execute() {
        return null;
    }
}
