package com.gjk.javabasis.util.byteUtil;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.util.NumberUtil;

public class ByteWriteUtil {
    /**
     * 写入16进制字符串
     */
    public static ByteBuffer writeHexString(ByteBuffer out, String src) {
        int m = 0, n = 0;
        int iLen = src.length() / 2; //计算长度
        byte[] ret = new byte[iLen]; //分配存储空间
        for (int i = 0; i < iLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = (byte) (Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)) & 0xFF);
        }
        return out.put(ret);
    }

    /**
     * 写入16进制字符串，不足长度补齐
     *
     * @param out
     * @param src
     * @return
     */
    public static ByteBuffer writeHexStringWithPad(ByteBuffer out, String src, int size) {
        int m = 0, n = 0;
        if (src == null) {
            src = "";
        }
        if (src.length() < size) {
            src = StringUtils.leftPad(src, size, "0");
        }
        int iLen = src.length() / 2; //计算长度
        byte[] ret = new byte[iLen]; //分配存储空间
        for (int i = 0; i < iLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = (byte) (Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)) & 0xFF);
        }
        return out.put(ret);
    }

    /**
     * 写入字符串
     *
     * @param out
     * @param str
     * @return
     */
    public static ByteBuffer writeString(ByteBuffer out, String str) {
        byte[] bytes = str.getBytes();
        return out.put(bytes);
    }

    /**
     * 写入字符串，不足位补0
     *
     * @param out
     * @param str
     * @param size
     * @return
     */
    public static ByteBuffer writeStringWithPad(ByteBuffer out, String str, int size) {
        if (str == null) {
            str = "";
        }
        if (str.length() < size) {
            str = StringUtils.leftPad(str, size, "0");
        }
        byte[] bytes = str.getBytes();
        return out.put(bytes);
    }

    /**
     * 写入字符串，不足位补0
     *
     * @param out
     * @param str
     * @param size
     * @return
     */
    public static ByteBuffer writeStringWithLeftBytePad(ByteBuffer out, String str, int size) {
        if (str == null) {
            str = "";
        }
        byte[] bytes = str.getBytes();
        if (bytes.length < size) {
            out.put(new byte[size-bytes.length]);
        }
        out.put(bytes);
        return out;
    }

    /**
     * 写入字符串，不足位补0
     *
     * @param out
     * @param str
     * @param size
     * @return
     */
    public static ByteBuffer writeStringWithRightBytePad(ByteBuffer out, String str, int size) {
        if (str == null) {
            str = "";
        }
        byte[] bytes = str.getBytes();
        out.put(bytes);
        if (bytes.length < size) {
            out.put(new byte[size-bytes.length]);
        }
        return out;
    }

    /**
     * 写入单字节
     */
    public static ByteBuffer writeByte(ByteBuffer out, byte b) {
        return out.put(b);
    }

    /**
     * 写入字节数组
     */
    public static ByteBuffer writeBytes(ByteBuffer out, byte[] b) {
        return out.put(b);
    }

    /**
     * 写入短整形
     */
    public static ByteBuffer writeShort(ByteBuffer out, int b) {
        byte[] src = new byte[2];
        src[0] = (byte) ((b >> 8) & 0xFF);
        src[1] = (byte) (b & 0xFF);
        return out.put(src);
    }

    /**
     * 写入小端短整形
     */
    public static ByteBuffer writeShortLE(ByteBuffer out, int b) {
        byte[] src = new byte[2];
        src[1] = (byte) ((b >> 8) & 0xFF);
        src[0] = (byte) (b & 0xFF);
        return out.put(src);
    }

    public static ByteBuffer writeDoubleLE(ByteBuffer out, double v, int scale) {
        byte[] src = new byte[4];
        int s = (int) (NumberUtil.mul(v , Math.pow(10, scale)));
        src[3] = (byte) ((s >> 24) & 0xFF);
        src[2] = (byte) ((s >> 16) & 0xFF);
        src[1] = (byte) ((s >> 8) & 0xFF);
        src[0] = (byte) (s & 0xFF);
        return out.put(src);
    }

    public static ByteBuffer writeCP56Time2a(ByteBuffer out, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        writeShortLE(out, calendar.get(Calendar.SECOND) * 1000);
        out.put((byte) calendar.get(Calendar.MINUTE));
        out.put((byte) calendar.get(Calendar.HOUR_OF_DAY));
        out.put((byte) calendar.get(Calendar.DAY_OF_MONTH));
        out.put((byte) (calendar.get(Calendar.MONTH) + 1));
        out.put((byte) (calendar.get(Calendar.YEAR) % 100));
        return out;
    }
}
