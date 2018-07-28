package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class DefaultInputParamCheckerTest {
    @Test
    public void execute_invalid_project_target_folder() {
        String testBaseDir = this.getClass().getClassLoader().getResource("").getPath();

        InputParams p = new InputParams();
        p.setSrcFileList(testBaseDir + File.separator + "src.txt");

        DefaultInputParamChecker checker = new DefaultInputParamChecker();
        // TODO
        ERROR_CODES errorCodes = checker.setParams(null).execute();

        assertEquals(ERROR_CODES.INVALID_PROJECT_TARGET_FOLDER, errorCodes);
    }

    @Test
    public void execute_INPUT_DIR_INVALID() {
        String testBaseDir = this.getClass().getClassLoader().getResource("").getPath();

        InputParams p = new InputParams();
        p.setSrcFileList("src.txt");

        DefaultInputParamChecker checker = new DefaultInputParamChecker();
        // TODO
        ERROR_CODES errorCodes = checker.setParams(null).execute();

        assertEquals(ERROR_CODES.INPUT_DIR_INVALID, errorCodes);
    }

    @Test
    public void execute_absolute_invalid_project_target_folder() {
        String testBaseDir = this.getClass().getClassLoader().getResource("").getPath();

        InputParams p = new InputParams();
        p.setSrcFileList("target/test-classes/reading/src.txt");

        DefaultInputParamChecker checker = new DefaultInputParamChecker();
        // TODO
        ERROR_CODES errorCodes = checker.setParams(null).execute();

        assertEquals(ERROR_CODES.SUCCESS, errorCodes);
        assertEquals("D:\\code\\scavengers\\target\\test-classes\\reading\\src.txt", p.getSrc().toString());
        assertEquals("D:\\code\\scavengers\\target\\test-classes\\reading", p.getProject().toString());
        assertEquals("D:\\code\\scavengers\\target\\test-classes\\reading\\target\\reading", p.getTarget().toString());
        assertEquals("D:\\code\\scavengers\\target\\test-classes\\reading\\export", p.getExport().toString());
    }
}