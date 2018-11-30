package com.zt.app.tool;


import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.LogMsgFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DefaultFileParser implements IFileParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFileParser.class);
    private File srcFileList = null;
    private List<Dir> dirs = new LinkedList<>();

    @Override
    public List<Dir> getDirs() {
        return dirs;
    }

    @Override
    public IFileParser setFile(File srcFileList) {
        this.srcFileList = srcFileList;
        return this;
    }

    @Override
    public IFileParser setFile(String dir) {
        this.srcFileList = new File(dir);
        return this;
    }

    @Override
    public ERROR_CODES check() {
        if (Objects.nonNull(this.srcFileList)) {
            if (this.srcFileList.exists() && this.srcFileList.isFile()) {
                return ERROR_CODES.SUCCESS;
            }
        }
        return ERROR_CODES.INPUT_DIR_INVALID;
    }

    @Override
    public String getName() {
        return "default-file-parse";
    }

    @Override
    public ERROR_CODES execute() {
        LOGGER.info(String.format(LogMsgFormat.PLUGIN_START, getName()));

        if (this.check() != ERROR_CODES.SUCCESS) {
            return ERROR_CODES.INPUT_DIR_INVALID;
        }


        LOGGER.info("read from file: " + this.srcFileList.toString());
        try (BufferedReader reader = new BufferedReader(new FileReader(srcFileList))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    dirs.add(new Dir().setSrcDir(line));
                    LOGGER.debug("get dir: " + line);
                }
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return ERROR_CODES.FILE_READ_ERROR;
        }

        LOGGER.info(toReport());
        return ERROR_CODES.SUCCESS;
    }

    @Override
    public String toReport() {
        StringBuilder report = new StringBuilder();
        report.append(String.format("get %d dir(s) from file", dirs.size()));
        return report.toString();
    }
}
