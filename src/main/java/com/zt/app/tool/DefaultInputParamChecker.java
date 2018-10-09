package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.INPUT_PARAMS;
import com.zt.app.tool.common.InputParams;
import com.zt.app.tool.common.LogMsgFormat;
import com.zt.app.tool.input.IInputParser;
import com.zt.app.tool.input.InputParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DefaultInputParamChecker implements IInputParamsChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultInputParamChecker.class);

    private InputParams params = new InputParams();

    Map<INPUT_PARAMS, String> paramsMap = new HashMap<>();

    @Override
    public IInputParamsChecker setParams(Map<INPUT_PARAMS, String> params) {
        return null;
    }

    @Override
    public InputParams getParams() {
        return this.params;
    }

    @Override
    public ERROR_CODES check() {
        return ERROR_CODES.SUCCESS;
    }

    @Override
    public String toReport() {
        File src = this.params.getSrc();
        File project = this.params.getProject();
        File target = this.params.getTarget();
        File export = this.params.getExport();
        StringBuilder report = new StringBuilder();
        report.append(String.format("\nsrc dir: %s\n", src));
        report.append(String.format("project dir: %s\n", project));
        report.append(String.format("target dir: %s\n", target));
        report.append(String.format("export dir: %s\n", export));

        return report.toString();
    }

    @Override
    public String getName() {
        return "default-input-param-check";
    }

    @Override
    public ERROR_CODES execute() {
        LOGGER.info(String.format(LogMsgFormat.PLUGIN_START, getName()));

        for (Map.Entry<INPUT_PARAMS, String> e : this.paramsMap.entrySet()) {
            IInputParser iInputParser = InputParserFactory.create(e.getKey());

            if (Objects.nonNull(iInputParser)) {
                iInputParser.setInputString(e.getValue()).setResultHolder(this.params).execute();
            }

        }
        LOGGER.info(this.toReport());
        // TODO
        return null;
    }
}
