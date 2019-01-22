package com.zt.app.tool.replace;


import com.siyuqxxx.tool.checker.string.StrCheckerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.Objects;

public class JavaClassFilter implements FileFilter {
    private String className = "";

    public JavaClassFilter setFileFilterPattern(File f) {
        if (Objects.nonNull(f) && f.exists()) {
            String name = f.getName();
            if (name.endsWith(".class")) {
                int classFileSuffixIndex = name.lastIndexOf(".class");
                if (classFileSuffixIndex > -1 && classFileSuffixIndex < name.length()) {
                    this.className = name.substring(0, classFileSuffixIndex);
                }
            }
        }
        return this;
    }

    public String getClassName() {
        return className;
    }

    @Deprecated
    public JavaClassFilter setClassName(String className) {
        if (StrCheckerFactory.createStrTrimChecker().check(className)) {
            this.className = className;
        }
        return this;
    }

    @Override
    public boolean accept(File pathname) {
        String name = pathname.getName();
        if (StrCheckerFactory.createStrTrimChecker().check(this.className)) {
            if (name.startsWith(this.className)) {
                String subName = name.substring(className.length());
                if (".class".equals(subName)) {
                    return true;
                } else {
                    return subName.startsWith("$") && subName.endsWith(".class");
                }
            }
        }
        return false;
    }
}
