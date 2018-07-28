package com.zt.app.tool.input;

import com.zt.app.tool.checker.dir.folder.FolderChecker;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProjectCheckerTest {

    @Test
    public void execute() {
        FolderChecker checker = new FolderChecker();
        checker.setDir("target\\test-classes\\reading");
        IInputChecker projectChecker = new ProjectChecker().setChecker(checker).setResultHolder(new InputParams());
        ERROR_CODES errorCode = projectChecker.execute();
        assertEquals(ERROR_CODES.SUCCESS, errorCode);
    }

    @Test
    public void execute_absolute() {
        FolderChecker checker = new FolderChecker();
        checker.setDir("D:\\code\\scavengers\\target\\test-classes\\reading");
        IInputChecker projectChecker = new ProjectChecker().setChecker(checker).setResultHolder(new InputParams());
        ERROR_CODES errorCode = projectChecker.execute();
        assertEquals(ERROR_CODES.SUCCESS, errorCode);
    }

    @Test
    public void execute_false() {
        FolderChecker checker = new FolderChecker();
        checker.setDir("target\\test-classes\\src.txt");
        IInputChecker projectChecker = new ProjectChecker().setChecker(checker).setResultHolder(new InputParams());
        ERROR_CODES errorCode = projectChecker.execute();
        assertEquals(ERROR_CODES.INVALID_PROJECT_DIR, errorCode);
    }

    @Test
    public void execute_without_target() {
        FolderChecker checker = new FolderChecker();
        checker.setDir("target\\test-classes");
        IInputChecker projectChecker = new ProjectChecker().setChecker(checker).setResultHolder(new InputParams());
        ERROR_CODES errorCode = projectChecker.execute();
        assertEquals(ERROR_CODES.INVALID_PROJECT_TARGET_FOLDER, errorCode);
    }
}