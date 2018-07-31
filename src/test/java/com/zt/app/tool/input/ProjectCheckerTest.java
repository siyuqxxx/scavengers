package com.zt.app.tool.input;

import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class ProjectCheckerTest {

    @Test
    public void execute() {
        IInputChecker projectChecker = new ProjectChecker().setDir("target\\test-classes\\reading").setResultHolder(new InputParams());
        ERROR_CODES errorCode = projectChecker.execute();
        assertEquals(ERROR_CODES.SUCCESS, errorCode);
    }

    @Test
    public void execute_absolute() {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String testProject = path + "reading" + File.separator;

        IInputChecker projectChecker = new ProjectChecker().setDir(testProject).setResultHolder(new InputParams());
        ERROR_CODES errorCode = projectChecker.execute();
        assertEquals(ERROR_CODES.SUCCESS, errorCode);
    }

    @Test
    public void execute_false() {
        IInputChecker projectChecker = new ProjectChecker().setDir("target\\test-classes\\src.txt").setResultHolder(new InputParams());
        ERROR_CODES errorCode = projectChecker.execute();
        assertEquals(ERROR_CODES.INVALID_PROJECT_DIR, errorCode);
    }
}