package com.zt.app.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class UTUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(UTUtil.class);

    public static class PATH {
        public static final String SRC_FILE_LIST_NAME = "reading\\src.txt";
        public static final String BASE_DIR = "C:\\Users\\SiyuQ\\code\\";
        public static final String TEST_BASE_DIR = UTUtil.class.getClassLoader().getResource("").getPath();
    }

    public static void deleteFolder(File srcFolder) {
        LOGGER.info(String.format("junit delete folder: %s", srcFolder.toString()));
        //获取该目录下的所有文件或者文件夹的File数组
        File[] fileArray = srcFolder.listFiles();
        if (fileArray != null) {
            //遍历该File数组，得到每一个File对象
            for (File file : fileArray) {
                //判断该对象是否是文件夹
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    LOGGER.debug("delete file   " + file.delete() + " - " + file.toString());//先删除文件，再删除文件夹
                }
            }
            LOGGER.debug(("delete folder " + srcFolder.delete() + " - " + srcFolder.toString()));
        }
    }
}
