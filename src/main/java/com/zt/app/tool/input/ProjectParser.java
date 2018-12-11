package com.zt.app.tool.input;

import com.zt.app.tool.checker.dir.DirCheckerFactory;
import com.zt.app.tool.common.ERROR_CODES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class ProjectParser extends AInputParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectParser.class);

    public ProjectParser() {
        super.setChecker(DirCheckerFactory.create(DirCheckerFactory.DIR_CHECKER.MVN_WEB_PROJECT));
    }

    @Override
    public String getName() {
        return "project-parser";
    }

    @Override
    public ERROR_CODES check() {

        String strSrcFileList = super.getInputString();
        File mvnProjectFolder = null;
        if ("".equals(strSrcFileList)) {
            // 检查当前执行路径下是否为 mvn 工程
            mvnProjectFolder = new File(getRuntimeDir());
            if (super.getChecker().check(mvnProjectFolder)) {
                super.getResultHolder().setProjectAndTarget(mvnProjectFolder);
                return ERROR_CODES.SUCCESS;
            }

            // 检查 srcListFile 所在目录是否为 mvn 工程
            mvnProjectFolder = super.getResultHolder().getSrc().getParentFile();
            if (super.getChecker().check(mvnProjectFolder)) {
                super.getResultHolder().setProjectAndTarget(mvnProjectFolder);
                return ERROR_CODES.SUCCESS;
            }
        }

        mvnProjectFolder = new File(strSrcFileList);
        if (super.getChecker().check(mvnProjectFolder)) {
            super.getResultHolder().setProjectAndTarget(mvnProjectFolder);
            return ERROR_CODES.SUCCESS;
        }
        LOGGER.debug(String.format("invalid project folder input dir. %s", strSrcFileList));


        LOGGER.debug(String.format("execute %s failed", this.getName()));
        return ERROR_CODES.INVALID_PROJECT_DIR;
    }

    /**
     * 打包为 jar 以后，无法再获取类路径；此时，取 jar 当前的执行路径
     * 主要是解决 debug + UT 时，路径和打包后不同的问题
     *
     * @return runtime dir
     */
    private String getRuntimeDir() {
        URL runtimeURL = this.getClass().getClassLoader().getResource("");
        return Objects.nonNull(runtimeURL) ? runtimeURL.getPath() : "";
    }

}
