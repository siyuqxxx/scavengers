package com.zt.app.tool.input;

import com.zt.app.tool.common.ERROR_CODES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ProjectChecker extends AInputChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectChecker.class);

    private String projectFormat = "target" + File.separator + "${projectName}";

    public void setProjectFormat(String projectFormat) {
        this.projectFormat = projectFormat;
    }

    @Override
    public String getName() {
        return "project-check";
    }

    @Override
    public ERROR_CODES check() {
        if (super.getChecker().check()) {
            File project = super.getChecker().getDir();
            String child = projectFormat.replace("${projectName}", project.getName());
            if (super.getChecker().setDir(project, child).check()) {
                File target = super.getChecker().getDir();
                super.getParams().setProject(project);
                super.getParams().setTarget(target);
                return ERROR_CODES.SUCCESS;
            } else {
                return ERROR_CODES.INVALID_PROJECT_TARGET_FOLDER;
            }
        } else {
            LOGGER.debug(String.format("execute %s failed", this.getName()));
            return ERROR_CODES.INVALID_PROJECT_DIR;
        }
    }
}
