package com.huayuan.domain.credit;

import com.huayuan.domain.loanapplication.TelephoneVerification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnson on 5/1/14.
 */
@Component
public class TvQuestionGenerator {

    public List<String> getRandomQuestions(final String city) {
        return null;
    }

    public String generate(TelephoneVerification tv) {
        List<String> questions = new ArrayList<>();
        questions.add(tv.getTvMemAns1());
        questions.add(tv.getTvMemAns2());
        questions.add();

    }

}
