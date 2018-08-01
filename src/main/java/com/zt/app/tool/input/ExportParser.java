package com.zt.app.tool.input;

import com.zt.app.tool.common.ERROR_CODES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

public class ExportParser extends AInputParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportParser.class);

    private String serverProjectDir = "";

    public void setServerProjectDir(String serverProjectDir) {
        if (Objects.nonNull(serverProjectDir)) {
            this.serverProjectDir = serverProjectDir;
        }
    }

    @Override
    public String getName() {
        return "export-check";
    }

    @Override
    public ERROR_CODES check() {
        if (super.getChecker().check()) {
            if (!this.serverProjectDir.isEmpty()) {
                File f = new File(super.getChecker().getDir(), this.serverProjectDir);
                if (f.mkdirs()) {
                    super.getResultHolder().setExport(f);
                } else {
                    super.getResultHolder().setExport(super.getChecker().getDir());
                    LOGGER.error("create server project dir failed.");
                }
            }
            return ERROR_CODES.SUCCESS;
        } else {
            LOGGER.debug(String.format("execute %s failed", this.getName()));
            return ERROR_CODES.INVALID_EXPORT_FOLDER;
        }

    }
}
