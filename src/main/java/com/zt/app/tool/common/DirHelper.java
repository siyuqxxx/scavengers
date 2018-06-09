package com.zt.app.tool.common;

import com.zt.app.tool.IDirChecker;

import java.util.Objects;

public class DirHelper {
    public static void check(Dir dir, IDirChecker checker) {
        String srcDir = dir.getSrcDir();
        if (Objects.nonNull(srcDir) && !srcDir.trim().isEmpty()) {
            ERROR_CODES errorCode = checker.setDir(srcDir).execute();
            if (errorCode != ERROR_CODES.SUCCESS) {
                dir.setError_code(ERROR_CODES.SRC_DIR_INVALID);
            }
        }

        String targetDir = dir.getTargetDir();
        if (Objects.nonNull(targetDir) && !targetDir.trim().isEmpty()) {
            ERROR_CODES errorCode = checker.setDir(targetDir).execute();
            if (errorCode != ERROR_CODES.SUCCESS) {
                dir.setError_code(ERROR_CODES.TARGET_DIR_INVALID);
            }
        }
    }

}
