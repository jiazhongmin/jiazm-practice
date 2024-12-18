package com.jiazm.practice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream与byte 互转工具类
 *
 * @version 1.0
 * @date 2024年6月13日
 */
public class InputStreamUtil {

    public static  InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    public static  byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
}
