package com.zt.app.tool.checker.dir;

import com.zt.app.tool.checker.dir.file.FileChecker;
import com.zt.app.tool.checker.dir.folder.FolderChecker;

public class DirCheckerFactory {
    public static IDirChecker createFileChecker() {
        return new FileChecker();
    }

    public static IDirChecker createFolderChecker() {
        return new FolderChecker();
    }
}
