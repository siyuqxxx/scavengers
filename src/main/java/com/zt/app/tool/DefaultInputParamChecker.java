package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

public class DefaultInputParamChecker implements IInputParamsCheck {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultInputParamChecker.class);

    private InputParams params = null;

    private IDirChecker checker = new DefaultDirChecker();

    @Override
    public IInputParamsCheck setParams(InputParams params) {
        if (Objects.nonNull(params)) {
            this.params = params;
        }
        return this;
    }

    @Override
    public InputParams getParams() {
        return this.params;
    }

    @Override
    public ERROR_CODES check() {
        String projectName = this.params.getProjectName();
        if (isStrEmpty(projectName, "input param project name is null or empty.")) {
            return ERROR_CODES.INPUT_PARAM_EMPTY;
        }

        String exportDir = this.params.getExportDir();
        ERROR_CODES errorCode = this.checker.setDir(exportDir).setType(IDirChecker.DIR_TYPE.FOLDER).execute();
        if (errorCode != ERROR_CODES.SUCCESS) {
            return errorCode;
        } else {
            this.params.setExportDir(new File(exportDir).toString());
        }

        String projectDir = this.params.getProjectDir();
        errorCode = this.checker.setDir(projectDir).setType(IDirChecker.DIR_TYPE.FOLDER).execute();
        if (errorCode != ERROR_CODES.SUCCESS) {
            return errorCode;
        } else {
            this.params.setExportDir(new File(projectDir).toString());
        }

        String serverProjectDir = this.params.getServerProjectDir();
        if (isStrEmpty(serverProjectDir, "input param server project dir is null or empty.")) {
            return ERROR_CODES.INPUT_PARAM_EMPTY;
        }

        String srcFileList = this.params.getSrcFileList();
        errorCode = this.checker.setDir(srcFileList).execute();
        if (errorCode != ERROR_CODES.SUCCESS) {
            LOGGER.error("");
        } else {
            this.params.setExportDir(new File(srcFileList).toString());
        }

        return ERROR_CODES.SUCCESS;
    }

    private boolean isStrEmpty(String projectName, String errorMsg) {
        if (Objects.isNull(projectName) || projectName.trim().isEmpty()) {
            LOGGER.error(errorMsg);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toReport() {
        return null;
    }

    @Override
    public String getName() {
        return "default-input-param-checker";
    }

    @Override
    public ERROR_CODES execute() {
        // 做一些必要的转换，使得这些路径便于后续使用
        String projectName = this.params.getProjectName();

        String exportDir = this.params.getExportDir();
        this.params.setExportDir(new File(exportDir).toString());

        String projectDir = this.params.getProjectDir();
        this.params.setExportDir(new File(projectDir).toString());

        String serverProjectDir = this.params.getServerProjectDir();

        String srcFileList = this.params.getSrcFileList();
        this.params.setExportDir(new File(srcFileList).toString());

        return ERROR_CODES.SUCCESS;
    }
}
