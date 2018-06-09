package com.zt.app.tool;

public interface IDirChecker extends IPlugin {
    enum DIR_TYPE {
        FILE, FOLDER;
    }

    IDirChecker setDir(String dir);

    IDirChecker setType(DIR_TYPE type);
}
