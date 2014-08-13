package com.huayuan.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Johnson on 6/26/14.
 */
public class Constants {
    public static final String REPLY_ANSWER_PATTERN = "#[A-F]{1,2}";
    public static final String COMPANY_QUESTION_PREFIX = "工作地址";
    public static final String HOME_QUESTION_PREFIX = "家庭住址";
    public static final String LONG_DATE_PATTERN = "yyyyMMddHHmmss";

    public static final String ACCESS_TOKEN_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={appSecret}";
    public static final String GET_USER_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={accessToken}&openid={openid}&lang=zh_CN";
    public static final String SEND_MESSAGE_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={accessToken}";
    public static final String CUSTOM_MESSAGE_TEMPLATE = "'{'\"touser\":\"{0}\",\"msgtype\":\"text\",\"text\":'{'\"content\":\"{1}\"'}}'";
    public static final int power[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    public static String toSBC(String source) {
        char c[] = source.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    public static boolean isNum(String val) {
        return !(val == null || "".equals(val)) && val.matches("^[0-9]*$");
    }

    public static int[] convertCharToInt(char[] ca) {
        int len = ca.length;
        int[] iArr = new int[len];
        try {
            for (int i = 0; i < len; i++) {
                iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return iArr;
    }

    public static int getPowerSum(int[] iArr) {
        int iSum = 0;
        if (power.length == iArr.length) {
            for (int i = 0; i < iArr.length; i++) {
                for (int j = 0; j < power.length; j++) {
                    if (i == j) {
                        iSum = iSum + iArr[i] * power[j];
                    }
                }
            }
        }
        return iSum;
    }

    public static String getCheckCode18(int iSum) {
        String sCode = "";
        switch (iSum % 11) {
            case 10:
                sCode = "2";
                break;
            case 9:
                sCode = "3";
                break;
            case 8:
                sCode = "4";
                break;
            case 7:
                sCode = "5";
                break;
            case 6:
                sCode = "6";
                break;
            case 5:
                sCode = "7";
                break;
            case 4:
                sCode = "8";
                break;
            case 3:
                sCode = "9";
                break;
            case 2:
                sCode = "x";
                break;
            case 1:
                sCode = "0";
                break;
            case 0:
                sCode = "1";
                break;
        }
        return sCode;
    }

    public static boolean validateIdCard18(String idCard) {
        boolean bTrue = false;
        if (idCard.length() == 18) {
            String code17 = idCard.substring(0, 17);
            String code18 = idCard.substring(17, 18);
            if (isNum(code17)) {
                char[] cArr = code17.toCharArray();
                int[] iCard = convertCharToInt(cArr);
                int iSum17 = getPowerSum(iCard);
                String val = getCheckCode18(iSum17);
                if (val.length() > 0) {
                    if (val.equalsIgnoreCase(code18)) {
                        bTrue = true;
                    }
                }
            }
        }
        return bTrue;
    }

    public static String getBlockCode(final Integer overDays) {
        if (overDays < 31) return "D";
        if (overDays < 91) return "E";
        if (overDays < 181) return "F";
        if (overDays < Integer.MAX_VALUE) return "G";
        return StringUtils.EMPTY;
    }
}
