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

    @Value("${weChat.tvMessageTemplate}")
    private String messageTemplate;


    public String getQuestion(List<String> questions) {
        List<String> qs = new ArrayList<>();
        for (int i = 1; i <= questions.size(); i++) {
            qs.add(String.format("%d:%s", i, questions.get(i - 1)));
        }
        return StringUtils.join(qs, "\n");
    }

    private String getCity(TelephoneVerification tv) {
        final String mobilePhone = tv.getApplication().getMember().getMobile();
        return valueMobileAreaRepository.findBySevenPrefix(StringUtils.substring(mobilePhone, 0, 7)).getCity();
    }

    public String generate(TelephoneVerification tv) {
        List<String> questions = new ArrayList<>();
        questions.add(tv.getTvMemAns1());
        if (!tv.getTvMemAns1().equals(tv.getTvMemAns2()))
            questions.add(tv.getTvMemAns2());
        for (String question : tvRepository.findRandomAddress(getCity(tv))) {
            questions.add(question);
        }
        Collections.shuffle(questions);
        return MessageFormat.format(messageTemplate, tv.getTvQues1(), tv.getTvQues2(), getQuestion(questions));
    }
}
