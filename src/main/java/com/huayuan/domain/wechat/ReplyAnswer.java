package com.huayuan.domain.wechat;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.huayuan.common.util.Constants.*;

/**
 * Created by dell on 14-5-5.
 */
public class ReplyAnswer {
    private String answer;

    public boolean isValidAnswer() {
        return StringUtils.isNotEmpty(answer) && answer.matches(REPLY_ANSWER_PATTERN);
    }

    public ReplyAnswer(final String answer) {
        this.answer = answer;
    }

    private String extract(final String singleAnswer, final String question) {
        String regEx = "(?<=" + singleAnswer + ":)\\S*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(question);
        return m.find() ? m.group() : null;
    }

    public String getRealAnswer1(String question) {
        if (StringUtils.contains(question, HOME_QUESTION_PREFIX)) {
            return extract(answer.substring(1, 2), question);
        }
        return null;
    }

    public String getRealAnswer2(String question) {
        if (StringUtils.contains(question, COMPANY_QUESTION_PREFIX)) {
            if (StringUtils.contains(question, HOME_QUESTION_PREFIX)) {
                return extract(answer.substring(2, 3), question);
            } else {
                return extract(answer.substring(1, 2), question);
            }
        }
        return null;
    }
}