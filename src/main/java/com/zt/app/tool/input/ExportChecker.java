package com.zt.app.tool.input;

import com.zt.app.tool.common.ERROR_CODES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

public class ExportChecker extends AInputChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportChecker.class);

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
                File f = new File(super.getChecker().getFinalDir(), this.serverProjectDir);
                if (f.mkdirs()) {
                    super.getParams().setExport(f);
                } else {
                    super.getParams().setExport(super.getChecker().getFinalDir());
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
