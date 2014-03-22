package com.huayuan.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Li jiWang on 14-3-22.
 */
public class StringUtils {

    /**
     * GB编码转换成UTF-8
      * @param str
     * @return
     */
    public static String gbToUtf8(String str) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < str.length(); i++) {

            String s = str.substring(i, i + 1);

            if (s.charAt(0) > 0x80) {

                try {
                    byte[] bytes = s.getBytes("Unicode");


                    String binaryStr = "";

                    for (int j = 2; j < bytes.length; j += 2) {

                        // the first byte
                        String hexStr = getHexString(bytes[j + 1]);

                        String binStr = getBinaryString(Integer.valueOf(hexStr, 16));

                        binaryStr += binStr;
                        // the second byte
                        hexStr = getHexString(bytes[j]);
                        binStr = getBinaryString(Integer.valueOf(hexStr, 16));

                        binaryStr += binStr;
                    }
                    // convert unicode to utf-8
                    String s1 = "1110" + binaryStr.substring(0, 4);

                    String s2 = "10" + binaryStr.substring(4, 10);

                    String s3 = "10" + binaryStr.substring(10, 16);

                    byte[] bs = new byte[3];

                    bs[0] = Integer.valueOf(s1, 2).byteValue();

                    bs[1] = Integer.valueOf(s2, 2).byteValue();

                    bs[2] = Integer.valueOf(s3, 2).byteValue();

                    String ss = new String(bs, "UTF-8");

                    sb.append(ss);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

                sb.append(s);
            }
        }
        return sb.toString();

    }

    private static String getHexString(byte b) {
        String hexStr = Integer.toHexString(b);
        int m = hexStr.length();
        if (m < 2) {
            hexStr = "0" + hexStr;
        } else {
            hexStr = hexStr.substring(m - 2);
        }
        return hexStr;
    }

    private static String getBinaryString(int i) {
        String binaryStr = Integer.toBinaryString(i);
        int length = binaryStr.length();
        for (int l = 0; l < 8 - length; l++) {
            binaryStr = "0" + binaryStr;
        }
        return binaryStr;
    }

    /**
     * 判断字符串的编码
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }
}
