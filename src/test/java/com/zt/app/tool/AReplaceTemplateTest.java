package com.zt.app.tool;

import com.zt.app.tool.common.InputParams;
import com.zt.app.tool.common.ReplacePattern;
import com.zt.app.tool.replace.DefaultReplacer;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class AReplaceTemplateTest {

    @Test
    public void pickTargetFromSrc() {
        String testBaseDir = this.getClass().getClassLoader().getResource("").getPath();
        InputParams params = new InputParams();
        params.setExportDir("");
        params.setSrcFileList(testBaseDir + File.separator + "src.txt");
        params.setServerProjectDir("/opt/tomcat/webapps/");
        params.setProjectDir(testBaseDir + File.separator + "reading");

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

        DefaultInputParamChecker checker = new DefaultInputParamChecker();
        checker.setParams(params);

        DefaultFileParser reader = new DefaultFileParser();

        DefaultReplacer replacer = new DefaultReplacer();
        replacer.setPatterns(patterns);

        DefaultScavenger scavenger = new DefaultScavenger();

        AReplaceTemplate template = new AReplaceTemplate();
        template.setChecker(checker);
        template.setReader(reader);
        template.setReplacer(replacer);
        template.setScavenger(scavenger);
        template.setParams(params);

        template.pickTargetFromSrc();
    }
}