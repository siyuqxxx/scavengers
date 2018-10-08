package com.zt.app.tool.checker;

public interface IChecker<T> {
    String getName();

    boolean check(T obj);
}
