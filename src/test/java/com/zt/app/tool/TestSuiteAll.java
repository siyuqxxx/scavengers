package com.zt.app.tool;

import com.zt.app.tool.input.DefaultInputParamCheckerTest;
import com.zt.app.tool.input.parser.TestSuiteInput;
import com.zt.app.tool.read.DefaultSrcListReaderTest;
import com.zt.app.tool.replace.DefaultReplacerTest;
import com.zt.app.tool.replace.JavaClassFilterTest;
import com.zt.app.tool.scavengers.DefaultScavengerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DefaultInputParamCheckerTest.class,
        DefaultReplacerTest.class,
        DefaultScavengerTest.class,
        DefaultSrcListReaderTest.class,
        TestSuiteInput.class,
        JavaClassFilterTest.class,
        AReplaceTemplateTest.class})
public class TestSuiteAll {
    /*
     * 1.测试套件就是组织测试类一起运行的
     *
     * 写一个作为测试套件的入口类，这个类里不包含其他的方法
     * 更改测试运行器Suite.class
     * 将要测试的类作为数组传入到Suite.SuiteClasses（{}）
     */
}
