package com.zt.app.tool.read;

import com.zt.app.tool.UTUtil;
import com.zt.app.tool.common.ERROR_CODES;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefaultSrcListReaderTest {
    @Test
    public void execute_null_input() {
        DefaultSrcListReader parser = new DefaultSrcListReader();
        ERROR_CODES result = parser.execute();
        assertEquals(ERROR_CODES.INPUT_DIR_INVALID, result);

        result = null;
        parser.setFile("   ");
        result = parser.execute();
        assertEquals(ERROR_CODES.INPUT_DIR_INVALID, result);
    }

    @Test
    public void execute_invalid_input() {
        DefaultSrcListReader parser = new DefaultSrcListReader();

        parser.setFile("a.txt");
        ERROR_CODES result = parser.execute();
        assertEquals(ERROR_CODES.INPUT_DIR_INVALID, result);
    }

    @Test
    public void execute() {
        DefaultSrcListReader parser = new DefaultSrcListReader();

        String path = this.getClass().getClassLoader().getResource("").getPath();
        parser.setFile(path + UTUtil.PATH.SRC_FILE_LIST_NAME);
        ERROR_CODES result = parser.execute();
        assertEquals(102, parser.getDirs().size());
        assertEquals(ERROR_CODES.SUCCESS, result);
    }
}