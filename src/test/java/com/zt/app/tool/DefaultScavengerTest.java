package com.zt.app.tool;

import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DefaultScavengerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultScavengerTest.class);

    @Test
    public void execute() {
        File testBasePath = new File(UTUtil.PATH.TEST_BASE_DIR);
        InputParams inputParams = new InputParams();
        inputParams.setProject(new File(testBasePath, "reading"));
        inputParams.setTarget(new File(inputParams.getProject(), "target" + File.separator + "reading"));
        inputParams.setSrc(new File(testBasePath, UTUtil.PATH.SRC_FILE_LIST_NAME));
        inputParams.setExport(new File(inputParams.getProject(), "export"));

        List<Dir> dirs = new LinkedList<>();
        dirs.add(new Dir().setTargetDir("WEB-INF/classes/com/reading/controller/admin/AdminRobotController.class"));
        dirs.add(new Dir().setTargetDir("WEB-INF/classes/com/reading/data/mapping/RobotConsultRecordMapper.xml"));
        dirs.add(new Dir().setTargetDir("WEB-INF/html/admin/robotConfig_add.html"));
        dirs.add(new Dir().setTargetDir("WEB-INF/classes/com/reading/data/dao/LibraryMapper.java"));

        DefaultScavenger scavenger = new DefaultScavenger();
        scavenger.setParams(inputParams);
        scavenger.setDirs(dirs);

        ERROR_CODES errorCodes = scavenger.execute();
        assertEquals(ERROR_CODES.SUCCESS, errorCodes);
        long successCount = dirs.stream().filter(d -> d.getErrorCode() == ERROR_CODES.SUCCESS).count();
        assertEquals(3L, successCount);

        UTUtil.deleteFolder(inputParams.getExport());
    }
}