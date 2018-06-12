package com.zt.app.tool;

import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.LogMsgFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultScavenger implements IScavenger {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultScavenger.class);
    private List<Dir> dirs = new LinkedList<>();

    private String resultFolder = "";

    private String prefixPath = "";

    private IDirChecker checker = new DefaultDirChecker();

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
        ERROR_CODES errorCodes = checker.setDir(this.resultFolder).setType(IDirChecker.DIR_TYPE.FOLDER).execute();

        if (errorCodes != ERROR_CODES.SUCCESS) {
            return errorCodes;
        }

        List<Dir> validDirs = dirs.stream().filter(d -> d.getError_code() == ERROR_CODES.SUCCESS).collect(Collectors.toList());

        for (Dir dir : validDirs) {
            String targetDir = dir.getTargetDir();
            if (Objects.nonNull(targetDir) && !targetDir.trim().isEmpty()) {
                ERROR_CODES errorCode = checker.setDir(targetDir).execute();
                if (errorCode != ERROR_CODES.SUCCESS) {
                    dir.setError_code(ERROR_CODES.SRC_DIR_INVALID);
                }
            }
        }
        return ERROR_CODES.SUCCESS;
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
        LOGGER.info(String.format(LogMsgFormat.PLUGIN_START, getName()));
        ERROR_CODES errorCodes = check();
        if (errorCodes != ERROR_CODES.SUCCESS) {
            return errorCodes;
        }


        List<Dir> validDirs = dirs.stream().filter(d -> d.getError_code() == ERROR_CODES.SUCCESS).collect(Collectors.toList());

        for (Dir dir : validDirs) {

        }
        return null;
    }
}
