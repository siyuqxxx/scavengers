package com.zt.app.tool;

import com.zt.app.tool.common.INPUT_PARAMS;
import com.zt.app.tool.common.ReplacePattern;
import com.zt.app.tool.common.StrInputParams;
import com.zt.app.tool.replace.DefaultReplacer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class AReplaceTemplateTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UTUtil.class);

    @Test
    public void pickTargetFromSrc() {
        String testBaseDir = UTUtil.PATH.TEST_BASE_DIR;
        List<StrInputParams> inputParams = new LinkedList<>();
        inputParams.add(new StrInputParams().setKey(INPUT_PARAMS.SRC).setValue(testBaseDir + File.separator + "reading/src.txt"));
        inputParams.add(new StrInputParams().setKey(INPUT_PARAMS.PROJECT).setValue(testBaseDir + File.separator + "reading"));
        inputParams.add(new StrInputParams().setKey(INPUT_PARAMS.EXPORT).setValue(""));

        ReplacePattern javaPattern = new ReplacePattern();
        javaPattern.setName("java-pattern");
        javaPattern.setPattern("(?<=.*)/src/main/java/", "/WEB-INF/classes/");
        javaPattern.setPattern("\\.java", "\\.class");

        ReplacePattern mapperPattern = new ReplacePattern();
        mapperPattern.setName("mapper-pattern");
        mapperPattern.setPattern("(?<=.*)/src/main/java/", "/WEB-INF/classes/");
        mapperPattern.setPattern("\\.xml", "\\.xml");

        List<ReplacePattern> patterns = new LinkedList<>();
        patterns.add(javaPattern);
        patterns.add(mapperPattern);

        DefaultReplacer replacer = new DefaultReplacer();
        replacer.setPatterns(patterns);


        AReplaceTemplate template = new AReplaceTemplate();
        template.setReplacer(replacer);
        template.pickTargetFromSrc(inputParams);

        File exportFolder = new File(testBaseDir, "reading" + File.separator + "export");
        UTUtil.deleteFolder(exportFolder);
    }
}