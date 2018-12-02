package com.zt.app.tool.common;

import java.io.File;
import java.util.Objects;

public class InputParams {
    private File src = null;
    private File project = null;
    private File target = null;
    private File export = null;

    public File getSrc() {
        return this.src;
    }

    public void setSrc(File src) {
        if (Objects.nonNull(src)) {
            this.src = src.getAbsoluteFile();
        }
    }

    public File getProject() {
        return project;
    }

    public void setProject(File project) {
        if (Objects.nonNull(project)) {
            this.project = project.getAbsoluteFile();
        }
    }

    public File getTarget() {
        return target;
    }

    public void setTarget(File target) {
        if (Objects.nonNull(target)) {
            this.target = target.getAbsoluteFile();
        }
    }

    public void setProjectAndTarget(File mvnProject) {
        if (Objects.nonNull(mvnProject)) {
            this.project = mvnProject.getAbsoluteFile();
            this.target = new File(mvnProject, "target" + File.separator + mvnProject.getName());
        }
    }

    public File getExport() {
        return export;
    }

    public void setExport(File export) {
        if (Objects.nonNull(export)) {
            this.export = export.getAbsoluteFile();
        }
    }

    @Override
    public String toString() {
        return "InputParams{" +
                "src=" + src +
                ", project=" + project +
                ", target=" + target +
                ", export=" + export +
                '}';
    }
}
