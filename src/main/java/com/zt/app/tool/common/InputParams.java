package com.zt.app.tool.common;

public class InputParams {
    private String projectName = "";
    private String projectDir = "";
    private String srcFileList = "";
    private String exportDir = "";
    private String serverProjectDir = "";

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
                "projectName='" + projectName + '\'' +
                ", projectDir='" + projectDir + '\'' +
                ", srcFileList='" + srcFileList + '\'' +
                ", exportDir='" + exportDir + '\'' +
                ", serverProjectDir='" + serverProjectDir + '\'' +
                '}';
    }
}
