package com.zt.app.tool.checker.dir.folder.decorater;

import com.zt.app.tool.checker.dir.IDirChecker;
import com.zt.app.tool.checker.dir.folder.FolderChecker;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class MvnWebProjectTargetCheckerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MvnWebProjectTargetCheckerTest.class);

    @Test
    public void check() throws Exception {
        String path = this.getClass().getClassLoader().getResource("").getPath();

        String testProject = path + "reading" + File.separator;

        IDirChecker c = new MvnWebProjectTargetChecker().setComponent(new FolderChecker());
        LOGGER.debug(path);

        c.setDir(testProject);
        boolean isValid = c.check();
        assertTrue(isValid);
    }

}