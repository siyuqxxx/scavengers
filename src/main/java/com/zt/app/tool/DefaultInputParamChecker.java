package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import com.zt.app.tool.common.LogMsgFormat;
import com.zt.app.tool.common.StrInputParams;
import com.zt.app.tool.input.IInputParser;
import com.zt.app.tool.input.InputParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DefaultInputParamChecker implements IInputParamsChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultInputParamChecker.class);

    private InputParams params = new InputParams();

    private List<StrInputParams> strParams = new LinkedList<>();

    @Override
    public IInputParamsChecker setParams(List<StrInputParams> strParams) {
        if (Objects.nonNull(strParams) && !strParams.isEmpty()) {
            this.strParams.clear();
            this.strParams.addAll(strParams);
        }
        return this;
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
        for (StrInputParams p : this.strParams) {
            IInputParser iInputParser = InputParserFactory.create(p.getKey());

            if (Objects.nonNull(iInputParser)) {
                ERROR_CODES error_codes = iInputParser.setInputString(p.getValue()).setResultHolder(this.params).execute();
                if (error_codes != ERROR_CODES.SUCCESS) {
                    return error_codes;
                }
            }
        }
        LOGGER.info(this.toReport());
        return ERROR_CODES.SUCCESS;
    }
}
