package com.zt.app.tool.checker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        com.zt.app.tool.checker.dir.file.FileCheckerTest.class,
        com.zt.app.tool.checker.dir.folder.decorater.MvnWebProjectTargetCheckerTest.class})
public class TestSuiteChecker {

}