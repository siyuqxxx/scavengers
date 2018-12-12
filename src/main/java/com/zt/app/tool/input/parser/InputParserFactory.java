package com.zt.app.tool.input.parser;

import com.zt.app.tool.common.INPUT_PARAMS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class InputParserFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputParserFactory.class);

    private static Map<INPUT_PARAMS, Class<? extends IInputParser>> instance = new HashMap<>();

    static {
        instance.put(INPUT_PARAMS.SRC, SrcParser.class);
        instance.put(INPUT_PARAMS.PROJECT, ProjectParser.class);
        instance.put(INPUT_PARAMS.EXPORT, ExportParser.class);
    }

    public static IInputParser create(INPUT_PARAMS e) {
        try {
            return instance.get(e).newInstance();
        } catch (InstantiationException | IllegalAccessException exception) {
            LOGGER.error(exception.getMessage());
            return null;
        }
    }
}
