package com.zt.app.tool.common;

import java.io.File;

public class InputParams {
    private String projectDir = "";
    private String srcFileList = "";
    private String exportDir = "";
    private String serverProjectDir = "";
    private File src = null;
    private File project = null;
    private File target = null;
    private File export = null;

    public File getSrc() {
        return src;
    }

    public void setSrc(File src) {
        this.src = src;
    }

    public File getProject() {
        return project;
    }

    public void setProject(File project) {
        this.project = project;
    }

    public File getTarget() {
        return target;
    }

    public void setTarget(File target) {
        this.target = target;
    }

    public File getExport() {
        return export;
    }

    public void setExport(File export) {
        this.export = export;
    }

    public String getProjectDir() {
        return projectDir;
    }

    public void setProjectDir(String projectDir) {
        this.projectDir = projectDir;
    }

    public String getSrcFileList() {
        return srcFileList;
    }

    public void setSrcFileList(String srcFileList) {
        this.srcFileList = srcFileList;
    }

    public String getExportDir() {
        return exportDir;
    }

    public void setExportDir(String exportDir) {
        this.exportDir = exportDir;
    }

    public String getServerProjectDir() {
        return serverProjectDir;
    }

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
