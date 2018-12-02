package com.zt.app.tool;

import com.zt.app.tool.common.ERROR_CODES;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefaultFileParserTest {
    @Test
    public void execute_null_input() {
        DefaultFileParser parser = new DefaultFileParser();
        ERROR_CODES result = parser.execute();
        assertEquals(ERROR_CODES.INPUT_DIR_INVALID, result);

        result = null;
        parser.setFile("   ");
        result = parser.execute();
        assertEquals(ERROR_CODES.INPUT_DIR_INVALID, result);
    }

    @Test
    public void execute_invalid_input() {
        DefaultFileParser parser = new DefaultFileParser();

        parser.setFile("a.txt");
        ERROR_CODES result = parser.execute();
        assertEquals(ERROR_CODES.INPUT_DIR_INVALID, result);
    }

    @Test
    public void execute() {
        DefaultFileParser parser = new DefaultFileParser();

        String path = this.getClass().getClassLoader().getResource("").getPath();
        parser.setFile(path + UTUtil.PATH.FILE_NAME);
        ERROR_CODES result = parser.execute();
        assertEquals(102, parser.getDirs().size());
        assertEquals(ERROR_CODES.SUCCESS, result);
    }
}