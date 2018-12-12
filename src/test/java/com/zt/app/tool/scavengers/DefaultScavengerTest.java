package com.zt.app.tool.scavengers;

import com.zt.app.tool.UTUtil;
import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DefaultScavengerTest {
    @Test
    public void execute() {
        File testBasePath = new File(UTUtil.PATH.TEST_BASE_DIR);
        InputParams inputParams = new InputParams();
        inputParams.setProject(new File(testBasePath, "reading"));
        inputParams.setTarget(new File(inputParams.getProject(), "target" + File.separator + "reading"));
        inputParams.setSrc(new File(testBasePath, UTUtil.PATH.SRC_FILE_LIST_NAME));
        inputParams.setExport(new File(inputParams.getProject(), "export"));

        String adminRobotDir = "WEB-INF/classes/com/reading/controller/admin/AdminRobotController.class";
        Dir adminRobot = new Dir().setTargetDir(adminRobotDir);
        adminRobot.addTarget(new File(inputParams.getTarget(), adminRobotDir));

        String robotConsultRecordDir = "WEB-INF/classes/com/reading/data/mapping/RobotConsultRecordMapper.xml";
        Dir RobotConsultRecord = new Dir().setTargetDir(robotConsultRecordDir);
        RobotConsultRecord.addTarget(new File(inputParams.getTarget(), robotConsultRecordDir));

        String robotConfigDir = "WEB-INF/html/admin/robotConfig_add.html";
        Dir robotConfig = new Dir().setTargetDir(robotConfigDir);
        robotConfig.addTarget(new File(inputParams.getTarget(), robotConfigDir));

        String libraryDir = "WEB-INF/classes/com/reading/data/dao/LibraryMapper.java";
        Dir library = new Dir().setTargetDir(libraryDir);
        library.addTarget(new File(inputParams.getTarget(), libraryDir));

        List<Dir> dirs = new LinkedList<>();
        dirs.add(adminRobot);
        dirs.add(RobotConsultRecord);
        dirs.add(robotConfig);
        dirs.add(library);

        DefaultScavenger scavenger = new DefaultScavenger();
        scavenger.setParams(inputParams);
        scavenger.setDirs(dirs);

        ERROR_CODES errorCodes = scavenger.execute();
        assertEquals(ERROR_CODES.SUCCESS, errorCodes);
        long successCount = dirs.stream().filter(d -> d.getErrorCode() == ERROR_CODES.SUCCESS).count();
        assertEquals(3L, successCount);

        UTUtil.deleteFolder(inputParams.getExport());
    }

    @Test
    public void execute_has_inner_class() {
        File testBasePath = new File(UTUtil.PATH.TEST_BASE_DIR);
        InputParams inputParams = new InputParams();
        inputParams.setProject(new File(testBasePath, "reading"));
        inputParams.setTarget(new File(inputParams.getProject(), "target" + File.separator + "reading"));
        inputParams.setSrc(new File(testBasePath, UTUtil.PATH.SRC_FILE_LIST_NAME));
        inputParams.setExport(new File(inputParams.getProject(), "export"));

        File utilFolderTarget = new File(inputParams.getTarget(), "\\WEB-INF\\classes\\com\\reading\\utils\\");
        List<File> files = new LinkedList<>();
        files.add(new File(utilFolderTarget, "EnumUtil$WEEKLY.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$WechatThreeAuthorize.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$UPDATE_STATUS.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$SEAT_APPLY_STATUS.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$ROOM_TYPE.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$ROBOTCORPUS_STATUS.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$MESSAGERECORDSTATUS.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$LIS_AUTHOR_ENUM.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$LibraryParameterSetByType.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$IBEACON_MANUFACTURER.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$GUESS_YOU_LIKE_TYPE_ENUM.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$GUESS_YOU_LIKE_SOURCE_MODEL_ENUM.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$DEFAULT_PAGE.class"));
        files.add(new File(utilFolderTarget, "EnumUtil$CHOOSE_BOOK_PAGE.class"));
        files.add(new File(utilFolderTarget, "EnumUtil.class"));

        Dir dir = new Dir().setTargetDir("src/main/java/com/reading/utils/EnumUtil.java");
        dir.addAllTargets(files);

        List<Dir> dirs = new LinkedList<>();
        dirs.add(dir);

        DefaultScavenger scavenger = new DefaultScavenger();
        scavenger.setParams(inputParams);
        scavenger.setDirs(dirs);

        ERROR_CODES errorCodes = scavenger.execute();
        assertEquals(ERROR_CODES.SUCCESS, errorCodes);
        long successCount = dirs.stream().filter(d -> d.getErrorCode() == ERROR_CODES.SUCCESS).count();
        assertEquals(1L, successCount);


        UTUtil.deleteFolder(inputParams.getExport());
    }
}