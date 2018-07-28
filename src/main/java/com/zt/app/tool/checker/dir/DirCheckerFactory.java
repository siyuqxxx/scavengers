package com.zt.app.tool.checker.dir;

import com.zt.app.tool.checker.dir.file.FileChecker;
import com.zt.app.tool.checker.dir.folder.FolderChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DirCheckerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirCheckerFactory.class);

    private static Map<DIR_CHECKER, Class<? extends IDirChecker>> checkers = new HashMap<>();

    public enum DIR_CHECKER {
        FILE, FOLDER;
    }

    private static Map<DIR_CHECKER, Class<? extends IDirChecker>> getCheckers() {
        if (checkers.isEmpty()) {
            checkers.put(DIR_CHECKER.FILE, FileChecker.class);
            checkers.put(DIR_CHECKER.FOLDER, FolderChecker.class);
        }
        return checkers;
    }

    @Deprecated
    public static IDirChecker createFileChecker() {
        return new FileChecker();
    }

    @Deprecated
    public static IDirChecker createFolderChecker() {
        return new FolderChecker();
    }

    public static IDirChecker create(DIR_CHECKER checker) {
        if (getCheckers().containsKey(checker)) {
            try {
                return getCheckers().get(checker).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return new EmptyDirChecker();
    }

    public static IDirChecker create() {
        return create(null);
    }
}
