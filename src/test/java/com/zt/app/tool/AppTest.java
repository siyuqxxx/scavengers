package com.zt.app.tool;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private static final String FILE_NAME = "src.txt";

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        App app = new App();
        String path = App.class.getClassLoader().getResource("").getPath();
        System.out.println(path);
        List<String> srcFiles = app.getSrcFiles(path + FILE_NAME);

        assertEquals( 102, srcFiles.size());

        List<File> files = app.srcPath2TargetFile(srcFiles, "/src/main/java/", "\\target\\reading\\WEB-INF\\classes\\");

        assertEquals( 77, files.size());

        String targetPath = files.get(0).toString();

        assertEquals( "\\target\\reading\\WEB-INF\\classes\\com\\reading\\controller\\admin\\AdminRobotController.class", targetPath);

    }
}
