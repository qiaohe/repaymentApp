package com.huayuan.integration.wechat.domain;

import org.apache.commons.lang.StringUtils;

/**
 * Created by dell on 14-5-5.
 */
public class ReplyAnswer {
    private static final String REPLY_ANSWER_PATTERN = "#[A-F]{1,2}";
    private static final String COMPANY_QUESTION_PREFIX = "工作地址";
    private static final String HOME_QUESTION_PREFIX = "家庭住址";

    private String answer;

    public boolean isValidAnswer() {
        return StringUtils.isNotEmpty(answer) && answer.matches(REPLY_ANSWER_PATTERN);
    }

    public ReplyAnswer(final String answer) {
        this.answer = answer;
    }

    public String getRealAnswer1(String question) {
        if (StringUtils.contains(question, HOME_QUESTION_PREFIX)) {
            return StringUtils.substringBetween(question, answer.substring(1, 2) + ":", "\n");
        }
        return null;
    }

    public String getRealAnswer2(String question) {
        if (StringUtils.contains(question, COMPANY_QUESTION_PREFIX)) {
            if (StringUtils.contains(question, HOME_QUESTION_PREFIX)) {
                return StringUtils.substringBetween(question, answer.substring(2, 3) + ":", "\n");
            } else {
                return StringUtils.substringBetween(question, answer.substring(1, 2) + ":", "\n");
            }
        }
        return null;
    }

    public String getAnswer() {
        return answer;
    }
}