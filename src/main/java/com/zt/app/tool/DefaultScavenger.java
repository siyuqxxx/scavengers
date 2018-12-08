package com.zt.app.tool;

import com.zt.app.tool.checker.dir.DirCheckerFactory;
import com.zt.app.tool.checker.dir.IDirChecker;
import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.common.InputParams;
import com.zt.app.tool.common.LogMsgFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultScavenger implements IScavenger {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultScavenger.class);
    private List<Dir> dirs = new LinkedList<>();

    private InputParams params = null;

    private IDirChecker checker = DirCheckerFactory.create(DirCheckerFactory.DIR_CHECKER.FILE);

    @Override
    public IScavenger setParams(InputParams params) {
        if (Objects.nonNull(params)) {
            this.params = params;
        }
        return this;
    }

    @Override
    public IScavenger setDirs(List<Dir> dirs) {
        if (Objects.nonNull(dirs) && !dirs.isEmpty()) {
            this.dirs.clear();
            this.dirs.addAll(dirs);
        }
        return this;
    }

    @Override
    public ERROR_CODES check() {
        List<Dir> validDirs = dirs.stream().filter(d -> d.getErrorCode() == ERROR_CODES.SUCCESS).collect(Collectors.toList());

        File target = this.params.getTarget();

        for (Dir dir : validDirs) {
            String targetDir = dir.getTargetDir();
            if (Objects.nonNull(targetDir) && !targetDir.trim().isEmpty()) {
                if (targetDir.startsWith(File.separator) || targetDir.startsWith("/") || targetDir.startsWith("\\")) {
                    targetDir = targetDir.substring(1);
                }

                boolean isValid = checker.check(new File(target, targetDir));
                if (!isValid) {
                    dir.setErrorCode(ERROR_CODES.TARGET_DIR_INVALID);
                }
            }
        }

        return ERROR_CODES.SUCCESS;
    }

    @Override
    public String toReport() {
        Long srcDirInvalidCount = dirs.stream().filter(d -> d.getErrorCode() == ERROR_CODES.TARGET_DIR_INVALID).count();
        Long srcDirNotMatchAnyPatternCount = dirs.stream().filter(d -> d.getErrorCode() == ERROR_CODES.FILE_COPY_FAILED).count();
        Long successCount = dirs.stream().filter(d -> d.getErrorCode() == ERROR_CODES.SUCCESS).count();
        Long total = srcDirInvalidCount + srcDirNotMatchAnyPatternCount + successCount;
        StringBuilder report = new StringBuilder();
        report.append(String.format("\ntarget dir invalid count:            %4d\n", srcDirInvalidCount));
        report.append(String.format("file copy failed count:              %4d\n", srcDirNotMatchAnyPatternCount));
        report.append(String.format("success count:                       %4d\n", successCount));
        report.append("-----------------------------------------\n");
        report.append(String.format("total:                               %4d", total));
        return report.toString();
    }

    @Override
    public String getName() {
        return "default-scavenger";
    }

    @Override
    public ERROR_CODES execute() {
        LOGGER.info(String.format(LogMsgFormat.PLUGIN_START, getName()));
        ERROR_CODES errorCodes = check();
        if (errorCodes != ERROR_CODES.SUCCESS) {
            return errorCodes;
        }

        List<Dir> validDirs = dirs.stream().filter(d -> d.getErrorCode() == ERROR_CODES.SUCCESS).collect(Collectors.toList());

        for (Dir dir : validDirs) {
            try {
                File targetFile = new File(this.params.getTarget(), dir.getTargetDir());
                File exportFile = new File(this.params.getExport(), dir.getTargetDir());
                File exportParentPath = exportFile.getParentFile();
                if (!exportParentPath.exists()) {
                    if (!exportParentPath.mkdirs()) {
                        LOGGER.error("create parent path failed. " + exportParentPath.toString());
                        dir.setErrorCode(ERROR_CODES.CREATE_PARENT_PATH_FAILED);
                    }
                }
                Files.copy(targetFile.toPath(), exportFile.toPath());
            } catch (NoSuchFileException e) {
                LOGGER.error("no such file: " + e.getMessage());
            } catch (FileAlreadyExistsException e) {
                LOGGER.error("file already exists exception: " + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());
                dir.setErrorCode(ERROR_CODES.FILE_COPY_FAILED);
            }
        }

        LOGGER.info(this.toReport());
        return ERROR_CODES.SUCCESS;
    }
}
