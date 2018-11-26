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
        String testBaseDir = this.getClass().getClassLoader().getResource("").getPath();

        InputParams inputParams = new InputParams();
        inputParams.setExportDir(testBaseDir + "\\export\\");
        inputParams.setProject(new File(testBaseDir + File.separator + "reading"));
        inputParams.setTarget(new File(testBaseDir + File.separator + "reading" + File.separator + "target" + File.separator + "reading"));
        inputParams.setServerProjectDir("/opt/reading/");
        inputParams.setSrcFileList(testBaseDir + "reading/src.txt");

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

        LOGGER.debug(String.format("junit delete export folder: %s", inputParams.getExportDir()));
        deleteFolder(new File(inputParams.getExportDir()));
    }

    private static void deleteFolder(File srcFolder) {
        //获取该目录下的所有文件或者文件夹的File数组
        File[] fileArray = srcFolder.listFiles();
        if (fileArray != null) {
            //遍历该File数组，得到每一个File对象
            for (File file : fileArray) {
                //判断该对象是否是文件夹
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    LOGGER.debug(file.getName() + "..." + file.delete());//先删除问价再删除文件夹
                }
            }
            LOGGER.debug((srcFolder.getName() + "..." + srcFolder.delete()));
        }
    }
}