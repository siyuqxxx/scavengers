package com.zt.app.tool.checker.dir.folder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class MvnWebProjectTargetChecker extends FolderChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(MvnWebProjectTargetChecker.class);

    @Override
    public String getName() {
        return "mvn-web-project-target-checker";
    }

    @Override
    public boolean check() {
        if (super.check()) {
            File project = super.getDir();
            File target = new File(project, "target" + File.separator + project.getName());
            boolean isExists = target.exists();
            LOGGER.debug(String.format("%s: %s - %s", getName(), isExists, target));
            return isExists;
        }
        return false;
    }
}
