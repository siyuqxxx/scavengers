package com.zt.app.tool;

import java.util.List;

public abstract class AReplaceTemplate {
    public abstract void from(String file);

    public abstract void inProject(String name);

    public abstract void to(String folder);

    public abstract void setReplacer(List<IReplacer> replacers);

    public abstract void setDirChecker(IDirChecker dirChecker);

    public abstract void setFileParser(IFileParser reader);

    public abstract void setScavenger(IScavenger scavenger);

    public abstract void setLogPrinter(ILogPrinter logPrinter);

    public ERROR_CODES pickTargetFromSrc(List<Dir> path) {
        try {
            checkDirs(path);
            List<Dir> srcFiles = getScrFilesFrom(path);
            checkDirs(srcFiles);
            List<Dir> targetFiles = matchAndReplace(srcFiles);
            checkDirs(targetFiles);
            List<Dir> trashes = pickFrom(targetFiles);
            logPrinter(trashes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_CODES.SUCCESS;
    }

    protected abstract void logPrinter(List<Dir> trashes);

    protected abstract List<Dir> pickFrom(List<Dir> targetFiles);

    protected abstract List<Dir> matchAndReplace(List<Dir> srcFiles);

    protected abstract void checkDirs(List<Dir> srcFiles);

    protected abstract List<Dir> getScrFilesFrom(List<Dir> path);
}
