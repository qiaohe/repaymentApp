package com.huayuan.domain.recognizer;

import com.huayuan.common.exception.App;
import com.ocr.idcard.IDCard;
import com.ocr.idcard.IdCardScan;
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

    static {
        IdCardScan.addOcrServerIP(App.getInstance().getOcrServerHost(), App.getInstance().getOcrServerPort());
    }

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
        IDCard card =  IdCardScan.ocr("");
        return new IdCardInfo(card.Name, card.Folk, card.Address, card.CardNo);
    }
}
