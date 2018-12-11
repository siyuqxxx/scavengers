package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.INPUT_PARAMS;
import com.zt.app.tool.common.ReplacePattern;
import com.zt.app.tool.common.StrInputParams;
import com.zt.app.tool.replace.DefaultReplacer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        List<StrInputParams> inputParams = new LinkedList<>();
        if (Objects.nonNull(args)) {
            int argsLength = args.length;
            if (argsLength >= 1) {
                inputParams.add(new StrInputParams().setKey(INPUT_PARAMS.SRC).setValue(args[0]));
            } else {
                inputParams.add(new StrInputParams().setKey(INPUT_PARAMS.SRC).setValue("src.txt"));
            }

            if (argsLength >= 2) {
                inputParams.add(new StrInputParams().setKey(INPUT_PARAMS.PROJECT).setValue(args[1]));
            } else {
                inputParams.add(new StrInputParams().setKey(INPUT_PARAMS.PROJECT).setValue(""));
            }

            if (argsLength >= 3) {
                inputParams.add(new StrInputParams().setKey(INPUT_PARAMS.EXPORT).setValue(args[2]));
            } else {
                inputParams.add(new StrInputParams().setKey(INPUT_PARAMS.EXPORT).setValue(""));
            }
        } else {
            inputParams.add(new StrInputParams().setKey(INPUT_PARAMS.SRC).setValue("src.txt"));
        }

        LOGGER.info("get input params: " + inputParams);

        ReplacePattern javaPattern = new ReplacePattern();
        javaPattern.setName("java-pattern");
        javaPattern.setPattern("^/?src/main/java/", "/WEB-INF/classes/");
        javaPattern.setPattern("\\.java", "\\.class");

        ReplacePattern mapperPattern = new ReplacePattern();
        mapperPattern.setName("mapper-pattern");
        mapperPattern.setPattern("^/?src/main/java/", "/WEB-INF/classes/");

        ReplacePattern configPattern = new ReplacePattern();
        configPattern.setName("config-pattern");
        configPattern.setPattern("^/?src/main/resources/", "/WEB-INF/classes/");

        ReplacePattern htmlPattern = new ReplacePattern();
        htmlPattern.setName("html-pattern");
        htmlPattern.setPattern("^/?src/main/webapp/", "");

        List<ReplacePattern> patterns = new LinkedList<>();
        patterns.add(javaPattern);
        patterns.add(mapperPattern);
        patterns.add(configPattern);
        patterns.add(htmlPattern);

        DefaultReplacer replacer = new DefaultReplacer();
        replacer.setPatterns(patterns);

        LOGGER.info("start");
        ERROR_CODES error_codes = new AReplaceTemplate().setReplacer(replacer).pickTargetFromSrc(inputParams);
        LOGGER.info("end");
    }
}
