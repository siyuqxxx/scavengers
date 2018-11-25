package com.zt.app.tool.checker.dir.file;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FileCheckerTest {

    @Test
    public void checker() {
        String srcDir = "target\\test-classes\\src.txt";
        FileChecker checker = new FileChecker();
        boolean isValid = checker.check(srcDir);
        assertTrue(isValid);
    }

    @Test
    public void checker_true() {
        String srcDir = "D:\\code\\scavengers\\target\\test-classes\\src.txt";
        FileChecker checker = new FileChecker();
        boolean isValid = checker.check(srcDir);
        assertTrue(isValid);
    }
}