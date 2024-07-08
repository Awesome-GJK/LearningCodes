package com.gjk.javabasis.util.byteUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;

public class ByteReadUtil {
    /**
     * 协议解析内容起点
     */
    private static final int readIndex = 6;

    /**
     * 读取头信息长度
     *
     * @return
     */
    public static int readHeadLen(byte[] b) {
        return b[1] & 0xff;
    }

    /**
     * 读取头信息协议
     *
     * @return
     */
    public static int readPtc(byte[] b) {
        return b[5] & 0xff;
    }

    /**
     * 头信息SEQ
     */
    public static int readSeq(byte[] b) {
        int value = 0;
        for (int i = 0; i < 2; i++) {
            int shift = (2 - 1 - i) * 8;
            value += (b[i + 2] & 0xFF) << shift;
        }
        return value;
    }

    /**
     * 字节数组转16进制字符串
     *
     * @param b
     * @return
     */
    public static String readHexString(byte[] b) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret.append(hex.toUpperCase());
        }
        return ret.toString();
    }

    /**
     * 字节数组转16进制字符串
     *
     * @param b
     * @param start  开始下标
     * @param length 长度
     * @return
     */
    public static String readHexString(byte[] b, int start, int length) {
        StringBuilder ret = new StringBuilder();
        start += readIndex;
        for (int i = start; i < b.length && i < start + length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret.append(hex.toUpperCase());
        }
        return ret.toString();
    }

    /**
     * 小端 字节数组转16进制字符串
     *
     * @param b
     * @param start  开始下标
     * @param length 长度
     * @return
     */
    public static String readHexStringLE(byte[] b, int start, int length) {
        StringBuilder ret = new StringBuilder();
        start += readIndex;
        for (int i = start + length - 1; i >= start; i--) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret.append(hex.toUpperCase());
        }
        return ret.toString();
    }

    /**
     * 字节数组转字符串
     *
     * @param b
     * @param start  开始下标
     * @param length 长度
     * @return
     */
    public static String readString(byte[] b, int start, int length) {
        if (!StrUtil.padAfter("",length*2,"0").equals(readHexString(b,start,length))) {
            StringBuilder ret = new StringBuilder();
            start += readIndex;
            for (int i = start; i < b.length && i < start + length; i++) {
                String hex = String.valueOf((char) (b[i] & 0xFF));
                ret.append(hex.toUpperCase());
            }
            return ret.toString();
        }else{
            return "";
        }
    }

    /**
     * 小端 字节数组转字符串
     *
     * @param b
     * @param start  开始下标
     * @param length 长度
     * @return
     */
    public static String readStringLE(byte[] b, int start, int length) {
        StringBuilder ret = new StringBuilder();
        start += readIndex;
        for (int i = start + length - 1; i >= start; i--) {
            String hex = String.valueOf((char) (b[i] & 0xFF));
            ret.append(hex.toUpperCase());
        }
        return ret.toString();
    }

    /**
     * 字节转短整形
     *
     * @param b
     * @param start 开始下标
     * @return
     */
    public static int readShort(byte[] b, int start) {
        int value = 0;
        for (int i = 0; i < 2; i++) {
            int shift = (2 - 1 - i) * 8;
            value += (b[i + start + readIndex] & 0xFF) << shift;
        }
        return value;
    }


    /**
     * 小端字节转短整形
     *
     * @param b
     * @param start 开始下标
     * @return
     */
    public static int readShortLE(byte[] b, int start) {
        int value = 0;
        for (int i = 0; i < 2; i++) {
            int shift = i * 8;
            value += (b[i + start + readIndex] & 0xFF) << shift;
        }
        return value;
    }

    /**
     * 单字节转整形
     *
     * @param b
     * @param start 开始下标
     * @return
     */
    public static int readByte(byte[] b, int start) {
        start += readIndex;
        return b[start] & 0xff;
    }

    /**
     * byte 转boolean
     *
     * @param b
     * @param start
     * @return
     */
    public static boolean readBoolean(byte[] b, int start) {
        int intValue = readByte(b, start);
        return intValue > 0;
    }

    /**
     * 单字节转整形
     *
     * @param b
     * @param start 开始下标
     * @return
     */
    public static long readIntLE(byte[] b, int start) {
        long value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = i * 8;
            value += (b[i + start + readIndex] & 0xFF) << shift;
        }
        return value & 0xFFFFFFFFL;
    }


    /**
     * 2字节转double
     *
     * @param scale 小数位
     */
    public static double readFloat(byte[] b, int start, int scale) {
        int value = readShort(b, start);
        return new BigDecimal(value).divide(new BigDecimal(Math.pow(10, scale))).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 小端2字节转double
     *
     * @param scale 小数位
     */
    public static double readFloatLE(byte[] b, int start, int scale) {
        int value = readShortLE(b, start);
        return new BigDecimal(value).divide(new BigDecimal(Math.pow(10, scale))).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 字节转二进制
     */
    public static int[] readBinaryArray(byte[] b, int start, int length) {
        int[] array = null;
        if (1 == length) {
            array = new int[8];
        } else {
            array = new int[16];
        }
        String str = StringUtils.leftPad(Integer.toBinaryString(b[start + readIndex]), 8, "0");
        array[7] = Integer.parseInt(str.substring(0, 1));
        array[6] = Integer.parseInt(str.substring(1, 2));
        array[5] = Integer.parseInt(str.substring(2, 3));
        array[4] = Integer.parseInt(str.substring(3, 4));
        array[3] = Integer.parseInt(str.substring(4, 5));
        array[2] = Integer.parseInt(str.substring(5, 6));
        array[1] = Integer.parseInt(str.substring(6, 7));
        array[0] = Integer.parseInt(str.substring(7, 8));
        if (length > 1) {
            str = StringUtils.leftPad(Integer.toBinaryString(b[start + 1 + readIndex]), 8, "0");
            array[15] = Integer.parseInt(str.substring(0, 1));
            array[14] = Integer.parseInt(str.substring(1, 2));
            array[13] = Integer.parseInt(str.substring(2, 3));
            array[12] = Integer.parseInt(str.substring(3, 4));
            array[11] = Integer.parseInt(str.substring(4, 5));
            array[10] = Integer.parseInt(str.substring(5, 6));
            array[9] = Integer.parseInt(str.substring(6, 7));
            array[8] = Integer.parseInt(str.substring(7, 8));
        }
        return array;
    }

    /**
     * 字节转二进制拆分转整形数组
     * <00>：=正常；<01>：=超时；<10>： =不可信状态
     */
    public static int[] readBinaryInt(byte[] b, int start) {
        int[] array = new int[4];
        String str = StringUtils.leftPad(Integer.toBinaryString(b[start + readIndex]), 8, "0");
        array[3] = Integer.parseInt(str.substring(6, 8));
        array[2] = Integer.parseInt(str.substring(4, 6));
        array[1] = Integer.parseInt(str.substring(2, 4));
        array[0] = Integer.parseInt(str.substring(0, 2));
        return array;
    }

    /**
     * 字节转二进制字符串拆分
     */
    public static String[] readBinaryString(byte[] b, int start, int splitIndex) {
        String binaryString = StringUtils.leftPad(Integer.toBinaryString(b[start + readIndex]), 8, "0") + StringUtils.leftPad(Integer.toBinaryString(b[start + 1 + readIndex]), 8, "0");
        return new String[]{binaryString.substring(0, 12), binaryString.substring(12, 16)};
    }

    /**
     * 4字节转double
     *
     * @param scale 小数位
     */
    public static double readDoubleLE(byte[] b, int start, int scale) {
        long value = readIntLE(b, start);
        return new BigDecimal(value).divide(new BigDecimal(Math.pow(10, scale))).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double readLongDoubleLE(byte[] b, int start, int scale) {
        long value = HexUtil.toBigInteger(readHexStringLE(b, start, 5)).longValue();
        return new BigDecimal(value).divide(new BigDecimal(Math.pow(10, scale))).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 字节转CP56Time2a格式
     *
     * @param b
     * @param p
     * @return
     */
    public static Date readCP56Time2a(byte[] b, int p) {
        String year = "20" + StringUtils.leftPad(String.valueOf(readByte(b, p + 6)), 2, "0");
        String month = StringUtils.leftPad(String.valueOf(readByte(b, p + 5)), 2, "0");
        String weekAndDay = StringUtils.leftPad(Integer.toBinaryString(readByte(b, p + 4)), 8, "0");
        String day = weekAndDay.substring(weekAndDay.length() - 5, weekAndDay.length());
        String hour = StringUtils.leftPad(String.valueOf(readByte(b, p + 3)), 2, "0");
        String minute = StringUtils.leftPad(String.valueOf(readByte(b, p + 2)), 2, "0");
        int sec = readShortLE(b, p) / 1000;
        String second = StringUtils.leftPad(String.valueOf(sec), 2, "0");
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(year + "-" + month + "-" + Integer.parseInt(day, 2) + " " + hour + ":" + minute + ":" + second);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return date;
    }

}
