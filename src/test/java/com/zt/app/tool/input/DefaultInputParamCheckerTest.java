package com.zt.app.tool.input;

import com.zt.app.tool.UTUtil;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.INPUT_PARAMS;
import com.zt.app.tool.common.InputParams;
import com.zt.app.tool.common.StrInputParams;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DefaultInputParamCheckerTest {
    @Test
    public void execute_src_valid() {
        String testBaseDir = this.getClass().getClassLoader().getResource("").getPath();

        List<StrInputParams> ps = new LinkedList<>();
        ps.add(new StrInputParams().setKey(INPUT_PARAMS.SRC).setValue(testBaseDir + File.separator + "reading/src.txt"));

        ERROR_CODES errorCodes = new DefaultInputParamChecker().setParams(ps).execute();

        assertEquals(ERROR_CODES.SUCCESS, errorCodes);
    }

    @Test
    public void execute_INVALID_SRC_FILE() {
        String testBaseDir = this.getClass().getClassLoader().getResource("").getPath();

        List<StrInputParams> ps = new LinkedList<>();
        ps.add(new StrInputParams().setKey(INPUT_PARAMS.SRC).setValue("reading/src.txt"));

        ERROR_CODES errorCodes = new DefaultInputParamChecker().setParams(ps).execute();

        assertEquals(ERROR_CODES.INVALID_SRC_FILE, errorCodes);
    }

    @Test
    public void execute_absolute_invalid_project_target_folder() {
        String testBaseDir = this.getClass().getClassLoader().getResource("").getPath();

        List<StrInputParams> ps = new LinkedList<>();
        ps.add(new StrInputParams().setKey(INPUT_PARAMS.SRC).setValue("target/test-classes/reading/src.txt"));
        ps.add(new StrInputParams().setKey(INPUT_PARAMS.PROJECT).setValue(""));
        ps.add(new StrInputParams().setKey(INPUT_PARAMS.EXPORT).setValue(""));

        DefaultInputParamChecker checker = new DefaultInputParamChecker();
        ERROR_CODES errorCodes = checker.setParams(ps).execute();

        assertEquals(ERROR_CODES.SUCCESS, errorCodes);

        InputParams p = checker.getParams();
        assertEquals(new File(UTUtil.PATH.TEST_BASE_DIR, "reading\\src.txt"), p.getSrc());
        assertEquals(new File(UTUtil.PATH.TEST_BASE_DIR, "reading"), p.getProject());
        assertEquals(new File(UTUtil.PATH.TEST_BASE_DIR, "reading\\target\\reading"), p.getTarget());
        assertEquals(new File(UTUtil.PATH.TEST_BASE_DIR, "reading\\export"), p.getExport());
    }
}