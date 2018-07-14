package com.zt.app.tool.input;

import com.zt.app.tool.common.INPUT_PARAMS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class InputCheckerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputCheckerFactory.class);

    private static Map<INPUT_PARAMS, Class<? extends IInputChecker>> instance = new HashMap<>();


    public InputCheckerFactory() {
        this.instance.put(INPUT_PARAMS.SRC, SrcChecker.class);
        this.instance.put(INPUT_PARAMS.PROJECT, ProjectChecker.class);
        this.instance.put(INPUT_PARAMS.EXPORT, ExportChecker.class);
    }

    public static IInputChecker create(INPUT_PARAMS e) {
        try {
            return instance.get(e).newInstance();
        } catch (InstantiationException | IllegalAccessException exception) {
            LOGGER.error(exception.getMessage());
            return null;
        }
    }
}
