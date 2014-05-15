package com.huayuan.domain.credit;

import com.huayuan.domain.loanapplication.TelephoneVerification;
import com.huayuan.repository.ValueMobileAreaRepository;
import com.huayuan.repository.credit.TvRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Johnson on 5/1/14.
 */
@Component(value = "tvQuestionGenerator")
public class TvQuestionGenerator {
    @Inject
    private ValueMobileAreaRepository valueMobileAreaRepository;
    @Inject
    private TvRepository tvRepository;

    @Value("${weChat.tvMessageTemplateAll}")
    private String messageTemplateAll;

    @Value("${weChat.tvMessageTemplateCompanyOnly}")
    private String messageTemplateCompanyOnly;

    @Value("${weChat.tvMessageTemplateHomeOnly}")
    private String messageTemplateHomeOnly;


    public String getQuestion(List<String> questions) {
        List<String> qs = new ArrayList<>();
        for (int i = 1; i <= questions.size(); i++) {
            qs.add(String.format("%s:%s", Character.toChars(64 + i)[0], questions.get(i - 1)));
        }
        return StringUtils.join(qs, "\n");
    }

    private String getCity(TelephoneVerification tv) {
        final String mobilePhone = tv.getApplication().getMember().getMobile();
        return valueMobileAreaRepository.findBySevenPrefix(StringUtils.substring(mobilePhone, 0, 7)).getCity();
    }

    public String getTemplate(TelephoneVerification tv) {
        if (StringUtils.isNotEmpty(tv.getTvQues1()) && StringUtils.isNotEmpty(tv.getTvQues2()))
            return messageTemplateAll;
        if (StringUtils.isNotEmpty(tv.getTvQues1()) && StringUtils.isEmpty(tv.getTvQues2()))
            return messageTemplateHomeOnly;
        if (StringUtils.isEmpty(tv.getTvQues1()) && StringUtils.isNotEmpty(tv.getTvQues2()))
            return messageTemplateCompanyOnly;
        return null;
    }

    public String generate(TelephoneVerification tv) {
        List<String> questions = new ArrayList<>();
        if (StringUtils.isNotEmpty(tv.getTvQues1()))
            questions.add(tv.getTvMemAns1());
        if (StringUtils.isNotEmpty(tv.getTvQues2()))
            questions.add(tv.getTvMemAns2());
        for (String question : tvRepository.findRandomAddress(getCity(tv))) {
            questions.add(question);
        }
        Collections.shuffle(questions);
        return MessageFormat.format(getTemplate(tv), getQuestion(questions));
    }
}
