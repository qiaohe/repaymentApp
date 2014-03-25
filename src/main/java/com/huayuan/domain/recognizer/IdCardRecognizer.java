package com.huayuan.domain.recognizer;

import com.huayuan.common.imageprocessing.TesseractWrapper;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dell on 14-3-21.
 */
public class IdCardRecognizer {
    private static final int FILENAME_LENGTH = 12;
    private byte[] source;

    public IdCardRecognizer(byte[] source) {
        this.source = source;
    }

    public byte[] getSource() {
        return source;
    }

    private String getFilerName() {
        return RandomStringUtils.randomAlphanumeric(FILENAME_LENGTH);
    }

    private void saveFile(final String fileName) {
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileName));
            stream.write(getSource());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IdCardInfo recognize() {
        final String fileName = getFilerName();
        saveFile(fileName);
        TesseractWrapper wrapper = new TesseractWrapper();
        final String name = wrapper.recognize(null, 110, 30, 180, 55);
        final String nationality = wrapper.recognize(null, 262, 80, 80, 50);
        final String address = wrapper.recognize(null, 110, 160, 350, 100);
        final String idCardNumber = wrapper.recognize(null, 210, 250, 500, 100);
        return new IdCardInfo(name, nationality, address, idCardNumber);
    }
}
