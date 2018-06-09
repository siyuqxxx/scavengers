package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

public class DefaultDirChecker implements IDirChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFileParser.class);
    private String dir = "";

    public DefaultDirChecker setType(DIR_TYPE type) {
        this.type = type;
        return this;
    }

    private DIR_TYPE type = DIR_TYPE.FILE;

    @Override
    public IDirChecker setDir(String dir) {
        this.dir = dir;
        return this;
    }

    @Override
    public String getName() {
        return "default-file-checker";
    }

    @Override
    public ERROR_CODES execute() {
        LOGGER.debug("input dir: " + this.dir);
        try {
            if (Objects.isNull(this.dir) || this.dir.trim().isEmpty()) {
                throw new NullPointerException("file is not null or empty.");
            }

            File file = new File(this.dir);

            if (!file.exists()) {
                throw new IllegalArgumentException("file scheme is not exist.");
            } else if (type == DIR_TYPE.FILE) {
                if (!file.isFile()) {
                    throw new IllegalArgumentException("file scheme is not a file.");
                }
            } else if (type == DIR_TYPE.FOLDER) {
                if (!file.isDirectory()) {
                    throw new IllegalArgumentException("file scheme is not a folder.");
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage() + " " + this.dir);
            return ERROR_CODES.INPUT_DIR_INVALID;
        }

        return ERROR_CODES.SUCCESS;
    }
}
