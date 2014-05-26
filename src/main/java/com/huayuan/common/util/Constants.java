package com.huayuan.common.util;

/**
 * Created by Johnson on 6/26/14.
 */
public class Constants {
    public static final String REPLY_ANSWER_PATTERN = "#[A-F]{1,2}";
    public static final String COMPANY_QUESTION_PREFIX = "工作地址";
    public static final String HOME_QUESTION_PREFIX = "家庭住址";

    public static final String ACCESS_TOKEN_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={appSecret}";
    public static final String GET_USER_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={accessToken}&openid={openid}&lang=zh_CN";
    public static final String SEND_MESSAGE_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={accessToken}";
    public static final String CUSTOM_MESSAGE_TEMPLATE = "'{'\"touser\":\"{0}\",\"msgtype\":\"text\",\"text\":'{'\"content\":\"{1}\"'}}'";

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
}
