package com.zt.app.tool.replace;

import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ReplacePattern;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class DefaultReplacerTest {
    @Test
    public void execute() throws Exception {
        String path = this.getClass().getClassLoader().getResource("").getPath();

        List<Dir> dirs = new LinkedList<>();
        dirs.add(new Dir().setSrcDir(path + "src/main/java/com/reading/controller/admin/AdminRobotController.java"));
        dirs.add(new Dir().setSrcDir(path + "src/main/java/com/reading/data/mapping/RobotConsultRecordMapper.xml"));
        dirs.add(new Dir().setSrcDir(path + "src/main/webapp/WEB-INF/html/admin/robotConfig_add.html"));

        ReplacePattern javaPattern = new ReplacePattern();
        javaPattern.setName("java-pattern");
        javaPattern.setPattern("(?<=.*)/src/main/java/", "/target/reading/WEB-INF/classes/");
        javaPattern.setPattern("\\.java", "\\.class");

        DefaultReplaceUtil javaReplaceUtil = new DefaultReplaceUtil();
        javaReplaceUtil.setReplacePattern(javaPattern);

        ReplacePattern mapperPattern = new ReplacePattern();
        mapperPattern.setName("mapper-pattern");
        mapperPattern.setPattern("(?<=.*)/src/main/java/", "/target/reading/WEB-INF/classes/");
        mapperPattern.setPattern("\\.xml", "\\.xml");

        DefaultReplaceUtil mapperReplaceUtil = new DefaultReplaceUtil();
        mapperReplaceUtil.setReplacePattern(mapperPattern);

        List<IReplaceUnit> replaceUnits = new LinkedList<>();
        replaceUnits.add(javaReplaceUtil);
        replaceUnits.add(mapperReplaceUtil);

        DefaultReplacer replacer = new DefaultReplacer();
        replacer.setDirs(dirs).setReplaceUnits(replaceUnits).execute();

        System.out.println(dirs);
    }

    @Test
    public void RegexTest() {
        String path = this.getClass().getClassLoader().getResource("").getPath();

        String s = path + "src/main/java/com/reading/controller/admin/AdminRobotController.java";
        String regex = "/src/main/java/";
        boolean isMatch = s.matches(regex);

        String replace = s.replaceFirst(regex, "/target/");
        System.out.println(String.format("String: %s\nregex: %s\nis match: %s\nreplace: %s", s, regex, isMatch, replace));
        assertTrue(isMatch);
    }

    @Test
    public void RegexTest2() {
        String s = "Windows2000";
        String regex = "(?<=Wind)[a-zA-z]+(?=\\d+)";
//        boolean isMatch = Pattern.matches(regex, s);
        Matcher matcher = Pattern.compile(regex).matcher(s);
        boolean isMatch = matcher.find();
        String replace = s.replaceFirst(regex, "-----");
        System.out.println(String.format("String: %s\nregex: %s\nis match: %s\nreplace: %s", s, regex, isMatch, replace));
        assertTrue(isMatch);

    }
}