package com.zt.app.tool.input;

import com.zt.app.tool.checker.dir.DirCheckerFactory;
import com.zt.app.tool.checker.dir.IDirChecker;
import com.zt.app.tool.common.ERROR_CODES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ProjectChecker extends AInputChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectChecker.class);

    private String targetFormat = "target" + File.separator + "${projectName}";

    public void setTargetFormat(String targetFormat) {
        this.targetFormat = targetFormat;
    }

    public ProjectChecker() {
        super.setChecker(DirCheckerFactory.create(DirCheckerFactory.DIR_CHECKER.FOLDER));
    }

    @Override
    public String getName() {
        return "project-check";
    }

    @Override
    public ERROR_CODES check() {
        if (super.getChecker().check()) {
            return isMvnWebProject();
        } else {
            LOGGER.debug(String.format("execute %s failed", this.getName()));
            return ERROR_CODES.INVALID_PROJECT_DIR;
        }
    }

    private ERROR_CODES isMvnWebProject() {
        File project = super.getChecker().getDir();
        String targetProjectDir = getMvnWebProjectTargetFolder(project.getName());
        IDirChecker targetChecker = DirCheckerFactory.create(DirCheckerFactory.DIR_CHECKER.FOLDER);
        if (targetChecker.setDir(project, targetProjectDir).check()) {
            super.getResultHolder().setProject(project);
            super.getResultHolder().setTarget(targetChecker.getDir());
            return ERROR_CODES.SUCCESS;
        } else {
            return ERROR_CODES.INVALID_PROJECT_TARGET_FOLDER;
        }
    }

    private String getMvnWebProjectTargetFolder(String projectName) {
        return targetFormat.replace("${projectName}", projectName);
    }


}
