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
        String srcFileList = this.params.getSrcFileList();
        ERROR_CODES errorCode = this.checker.setDir(srcFileList).execute();
        if (errorCode == ERROR_CODES.SUCCESS) {
            this.params.setExport(new File(srcFileList));
        } else {
            LOGGER.error("src file list in invalid.");
            return errorCode;
        }

        String projectDir = this.params.getProjectDir();
        errorCode = this.checker.setDir(projectDir).setType(IDirChecker.DIR_TYPE.FOLDER).execute();
        if (errorCode == ERROR_CODES.SUCCESS) {
            this.params.setExportDir(new File(srcFileList).toString());
        } else {
            LOGGER.error("project dir is invalid.");
            LOGGER.error("try to check if the project dir is the dir where the src file list is located.");

            File parentFile = new File(srcFileList).getParentFile();
            boolean isProjectDir = isProjectDir(parentFile);
            if (isProjectDir) {
                LOGGER.debug("the project dir is the dir where the src file list is located.");
                this.params.setProjectDir(parentFile.toString());
            } else {
                LOGGER.error("the project dir is not the dir where the src file list is located.");

                LOGGER.error("try to check if the project dir is the currently dir.");
                isProjectDir = false;
                parentFile = new File("").getParentFile();
                isProjectDir = isProjectDir(parentFile);
                if (isProjectDir) {
                    LOGGER.debug("the project dir is the current dir.");
                    this.params.setProjectDir(parentFile.toString());
                } else {
                    LOGGER.error("the project dir not found.");
                    return ERROR_CODES.INVALID_PROJECT_DIR;
                }
            }
        }

        File projectDirFile = new File(projectDir);
        String projectName = projectDirFile.getName();

        File target = new File(projectDirFile, "target" + File.separator + projectName);
        if (target.exists() && target.isDirectory() && Objects.nonNull(target.listFiles())) {
            this.params.setTarget(target);
        } else {
            LOGGER.error("the target dir not found.");
            return ERROR_CODES.INVALID_PROJECT_TARGET_FOLDER;
        }

        String exportDir = this.params.getExportDir();
        errorCode = this.checker.setDir(exportDir).setType(IDirChecker.DIR_TYPE.FOLDER).execute();
        if (errorCode == ERROR_CODES.SUCCESS) {
            this.params.setExport(new File(exportDir));
        } else {
            LOGGER.error("the export dir not found.");
            return ERROR_CODES.INVALID_EXPORT_FOLDER;
        }

        String serverProjectDir = this.params.getServerProjectDir();
        if (Objects.isNull(serverProjectDir) || serverProjectDir.trim().isEmpty()) {
            LOGGER.error("input param server project dir is null or empty.");
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
        String src = this.params.getSrc().toString();
        String project = this.params.getProject().toString();
        String target = this.params.getTarget().toString();
        String export = this.params.getExport().toString();
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
        ERROR_CODES check = this.check();
        LOGGER.info(this.toReport());
        return check;
    }
}
