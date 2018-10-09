package com.zt.app.tool.input;

import com.zt.app.tool.checker.dir.DirCheckerFactory;
import com.zt.app.tool.common.ERROR_CODES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ExportParser extends AInputParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportParser.class);

    public ExportParser() {
        super.setChecker(DirCheckerFactory.create(DirCheckerFactory.DIR_CHECKER.FOLDER));
    }

    @Override
    public String getName() {
        return "export-parser";
    }

    @Override
    public ERROR_CODES check() {
        File exportFolder = new File(super.getInputString());
        // 判断路径是否存在，是否是文件夹
        if (super.getChecker().check(exportFolder)) {
            super.getResultHolder().setExport(exportFolder);
            return ERROR_CODES.SUCCESS;
        }

        // 尝试创建文件夹
        if (exportFolder.mkdirs()) {
            super.getResultHolder().setExport(exportFolder);
        } else {
            LOGGER.error("create export dir failed. " + exportFolder);
        }

        // 在当前项目下创建导出文件夹
        // todo 导出文件夹名称应该可以配置，方便后续维护和调整
        exportFolder = new File("", "export");
        if (exportFolder.mkdirs()) {
            super.getResultHolder().setExport(exportFolder);
        } else {
            LOGGER.error("create export dir failed. " + exportFolder);
        }

        LOGGER.debug(String.format("execute %s failed", this.getName()));
        return ERROR_CODES.INVALID_EXPORT_FOLDER;
    }
}
