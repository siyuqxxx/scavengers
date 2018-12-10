package com.zt.app.tool.replace;

import com.zt.app.tool.UTUtil;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JavaClassFilterTest {
    @Test
    public void setFileFilterPattern() {
        String testBaseDir = UTUtil.PATH.TEST_BASE_DIR;
        String targetFile = "\\reading\\target\\reading\\WEB-INF\\classes\\com\\reading\\utils\\EnumUtil.class";
        JavaClassFilter filter = new JavaClassFilter().setFileFilterPattern(new File(testBaseDir, targetFile));
        String className = filter.getClassName();
        assertEquals("EnumUtil", className);
    }

    @Test
    public void accept() {
        String testBaseDir = UTUtil.PATH.TEST_BASE_DIR;
        String targetFile = "\\reading\\target\\reading\\WEB-INF\\classes\\com\\reading\\utils\\EnumUtil.class";
        File f = new File(testBaseDir, targetFile);
        JavaClassFilter filter = new JavaClassFilter().setFileFilterPattern(f);
        File[] files = f.getParentFile().listFiles(filter);
        assertNotNull(files);
        assertEquals(15, files.length);
    }
}