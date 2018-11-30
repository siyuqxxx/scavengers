package com.zt.app.tool.common;

import java.io.File;
import java.util.Objects;

public class InputParams {
    @Deprecated
    private String srcFileList = "";
    @Deprecated
    private String projectDir = "";
    @Deprecated
    private String exportDir = "";
    @Deprecated
    private String serverProjectDir = "";
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

    @Deprecated
    public String getProjectDir() {
        return projectDir;
    }

    @Deprecated
    public void setProjectDir(String projectDir) {
        this.projectDir = projectDir;
    }

    @Deprecated
    public String getSrcFileList() {
        return srcFileList;
    }

    @Deprecated
    public void setSrcFileList(String srcFileList) {
        this.srcFileList = srcFileList;
    }

    @Deprecated
    public String getExportDir() {
        return exportDir;
    }

    @Deprecated
    public void setExportDir(String exportDir) {
        this.exportDir = exportDir;
    }

    @Deprecated
    public String getServerProjectDir() {
        return serverProjectDir;
    }

    @Deprecated
    public void setServerProjectDir(String serverProjectDir) {
        this.serverProjectDir = serverProjectDir;
    }

    @Override
    public String toString() {
        return "InputParams{" +
                "projectDir='" + projectDir + '\'' +
                ", srcFileList='" + srcFileList + '\'' +
                ", exportDir='" + exportDir + '\'' +
                ", serverProjectDir='" + serverProjectDir + '\'' +
                ", src=" + src +
                ", project=" + project +
                ", target=" + target +
                ", export=" + export +
                '}';
    }
}
