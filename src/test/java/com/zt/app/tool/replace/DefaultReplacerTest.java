package com.zt.app.tool.replace;

import com.zt.app.tool.UTUtil;
import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import com.zt.app.tool.common.ReplacePattern;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultReplacerTest {
    @Test
    public void execute() throws Exception {
        String path = UTUtil.PATH.TEST_BASE_DIR;
        InputParams p = new InputParams();
        p.setProjectAndTarget(new File(path, "reading"));

        List<Dir> dirs = new LinkedList<>();
        dirs.add(new Dir().setSrcDir("src/main/java/com/reading/controller/admin/AdminRobotController.java"));
        dirs.add(new Dir().setSrcDir("src/main/java/com/reading/data/mapping/RobotConsultRecordMapper.xml"));
        dirs.add(new Dir().setSrcDir("src/main/webapp/WEB-INF/html/admin/robotConfig_add.html"));
        dirs.add(new Dir().setSrcDir("/src/main/java/com/reading/data/dao/LibraryMapper.java"));

        ReplacePattern javaPattern = new ReplacePattern();
        javaPattern.setName("java-pattern");
        javaPattern.setPattern("^/?src/main/java/", "/target/reading/WEB-INF/classes/");
        javaPattern.setPattern("\\.java", "\\.class");

        ReplacePattern mapperPattern = new ReplacePattern();
        mapperPattern.setName("mapper-pattern");
        mapperPattern.setPattern("^/?src/main/java/", "/target/reading/WEB-INF/classes/");
        mapperPattern.setPattern("\\.xml", "\\.xml");

        List<ReplacePattern> patterns = new LinkedList<>();
        patterns.add(javaPattern);
        patterns.add(mapperPattern);

        DefaultReplacer replacer = new DefaultReplacer();
        replacer.setDirs(dirs).setParams(p).setPatterns(patterns).execute();

        long actualSuccessCount = replacer.getDirs().stream().filter(e -> e.getErrorCode() == ERROR_CODES.SUCCESS).count();
        assertEquals(2L, actualSuccessCount);

        long actualInvalidSrcFileCount = replacer.getDirs().stream().filter(e -> e.getErrorCode() == ERROR_CODES.INVALID_SRC_FILE).count();
        assertEquals(1L, actualInvalidSrcFileCount);
    }

    @Test
    public void RegexTest() {
        String s = "Windows2000";
        String regex = "(?<=Wind)[a-zA-z]+(?=\\d+)";
//        boolean isMatch = s.matches(regex);
//        boolean isMatch = Pattern.matches(regex, s);
        boolean isMatch = Pattern.compile(regex).matcher(s).find();
        String replace = s.replaceFirst(regex, "-----");
        System.out.println(String.format("String: %s\nregex: %s\nis match: %s\nreplace: %s", s, regex, isMatch, replace));
        assertTrue(isMatch);

    }

    @Test
    public void pathRegexTest() {
        String s = "src/main/java/com/reading/controller/admin/AdminRobotController.java";
        String regex = "^/?src/main/java/";
        String replacement = "/target/reading/WEB-INF/classes/";

        boolean isMatch = Pattern.compile(regex).matcher(s).find();
        assertTrue(isMatch);

        String replace = s.replaceFirst(regex, replacement);
        System.out.println(String.format("String: %s\nregex: %s\nis match: %s\nreplace: %s", s, regex, isMatch, replace));

    }
}