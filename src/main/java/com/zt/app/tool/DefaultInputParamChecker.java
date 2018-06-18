package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import com.zt.app.tool.common.LogMsgFormat;
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
        String srcFileList = this.params.getSrcFileList();
        ERROR_CODES errorCode = this.checker.setDir(srcFileList).execute();
        if (errorCode == ERROR_CODES.SUCCESS) {
            this.params.setSrc(new File(srcFileList));
        } else {
            LOGGER.debug("the src file list: " + srcFileList);
            LOGGER.error("src file list in invalid.");
            return errorCode;
        }

        String projectDir = this.params.getProjectDir();
        errorCode = this.checker.setDir(projectDir).setType(IDirChecker.DIR_TYPE.FOLDER).execute();
        if (errorCode == ERROR_CODES.SUCCESS) {
            this.params.setExportDir(new File(srcFileList).toString());
        } else {
            LOGGER.warn("project dir is invalid.");
            LOGGER.warn("try to check if the project dir is the dir where the src file list is located.");

            File parentFile = this.params.getSrc().getParentFile();
            boolean isProjectDir = isProjectDir(parentFile);
            if (isProjectDir) {
                LOGGER.error("the project dir will be the dir where the src file list is located.");
                this.params.setProjectDir(parentFile.toString());
                this.params.setProject(parentFile);
            } else {
                LOGGER.debug("the target dir: " + parentFile);
                LOGGER.warn("the project dir is not the dir where the src file list is located.");

                LOGGER.warn("try to check if the project dir is the currently dir.");
                isProjectDir = false;
                parentFile = new File("").getAbsoluteFile();
                LOGGER.debug("the currently dir: " + parentFile.toString());
                isProjectDir = isProjectDir(parentFile);
                if (isProjectDir) {
                    LOGGER.error("the project dir will be the current dir.");
                    this.params.setProjectDir(parentFile.toString());
                    this.params.setProject(parentFile);
                } else {
                    LOGGER.error("the project dir not found.");
                    return ERROR_CODES.INVALID_PROJECT_DIR;
                }
            }
        }

        String projectName = this.params.getProject().getName();

        File target = new File(this.params.getProject(), "target" + File.separator + projectName);
        if (target.exists() && target.isDirectory() && Objects.nonNull(target.listFiles())) {
            this.params.setTarget(target);
        } else {
            LOGGER.debug("the target dir: " + target);
            LOGGER.error("the target dir not found.");
            return ERROR_CODES.INVALID_PROJECT_TARGET_FOLDER;
        }

        String exportDir = this.params.getExportDir();
        errorCode = this.checker.setDir(exportDir).setType(IDirChecker.DIR_TYPE.FOLDER).execute();
        if (errorCode == ERROR_CODES.SUCCESS) {
            this.params.setExport(new File(exportDir));
        } else {
            LOGGER.warn("the export dir not found.");
            LOGGER.error("the export dir will be under the project dir.");
            this.params.setExport(new File(this.params.getProject(), "export"));
        }

        String serverProjectDir = this.params.getServerProjectDir();
        if (Objects.isNull(serverProjectDir) || serverProjectDir.trim().isEmpty()) {
            LOGGER.warn("input param server project dir is null or empty.");
        }

        return ERROR_CODES.SUCCESS;
    }

    private boolean isProjectDir(File parentFile) {
        File[] files = parentFile.listFiles();
        if (Objects.nonNull(files)) {
            for (File f : files) {
                if (f.isDirectory()) {
                    String name = f.getName();
                    if ("target".equals(name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toReport() {
        File src = this.params.getSrc();
        File project = this.params.getProject();
        File target = this.params.getTarget();
        File export = this.params.getExport();
        StringBuilder report = new StringBuilder();
        report.append(String.format("\nsrc dir: %s\n", src));
        report.append(String.format("project dir: %s\n", project));
        report.append(String.format("target dir: %s\n", target));
        report.append(String.format("export dir: %s\n", export));

        return report.toString();
    }

    @Override
    public String getName() {
        return "default-input-param-checker";
    }

    @Override
    public ERROR_CODES execute() {
        LOGGER.info(String.format(LogMsgFormat.PLUGIN_START, getName()));
        ERROR_CODES check = this.check();
        LOGGER.info(this.toReport());
        return check;
    }
}
