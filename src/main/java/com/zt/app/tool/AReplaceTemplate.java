package com.zt.app.tool;

import com.zt.app.tool.common.Dir;
import com.zt.app.tool.common.ERROR_CODES;
import com.zt.app.tool.replace.IReplacer;

import java.util.List;

public abstract class AReplaceTemplate {
    public abstract void from(String file);

    public abstract void inProject(String name);

    public abstract void to(String folder);

    public abstract void setReplacer(List<IReplacer> replacers);

    public abstract void setFileParser(IFileParser reader);

    public abstract void setScavenger(IScavenger scavenger);

    public ERROR_CODES pickTargetFromSrc(List<Dir> path) {
        try {
            List<Dir> srcFiles = getScrFilesFrom(path);
            List<Dir> targetFiles = matchAndReplace(srcFiles);
            pickFrom(targetFiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_CODES.SUCCESS;
    }

    protected abstract void pickFrom(List<Dir> targetFiles);

    protected abstract List<Dir> matchAndReplace(List<Dir> srcFiles);

    protected abstract List<Dir> getScrFilesFrom(List<Dir> path);
}
