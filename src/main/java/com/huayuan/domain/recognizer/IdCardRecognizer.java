package com.huayuan.domain.recognizer;

import java.io.FileInputStream;

/**
 * Created by dell on 14-3-21.
 */
public class IdCardRecognizer implements Recognizer {
    @Override
    public Object recognize(FileInputStream fileInputStream) {
        return new IdCardInfo();
    }
}
