package com.zt.app.tool.common;

import java.util.HashMap;
import java.util.Map;

public class ReplacePattern {
    String name = "";
    Map<String, String> patterns = new HashMap<>();

    public ReplacePattern setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setPattern(String regex, String replacer) {
        if (!patterns.containsKey(regex)) {
            patterns.put(regex, replacer);
        }
    }

    public Map<String, String> getPatterns() {
        return patterns;
    }
}
