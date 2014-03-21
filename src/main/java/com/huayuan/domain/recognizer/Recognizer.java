package com.huayuan.domain.recognizer;

import java.io.FileInputStream;

/**
 * Created by dell on 14-3-21.
 */
public interface Recognizer {
    public Object recognize(FileInputStream fileInputStream);
}
