package com.huayuan.domain.recognizer;

import com.huayuan.common.App;
import com.huayuan.domain.IdCard;
import com.huayuan.domain.SexEnum;
import com.ocr.idcard.IDCard;
import com.ocr.idcard.IdCardScan;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by dell on 14-3-21.
 */
public class IdCardRecognizer {
    private static final int FILENAME_LENGTH = 12;
    private static final String FILENAME_PATTERN = "%s/%s.jpg";

    private static final String VALID_DATE_DELIMITER = "-";
    private static final String VALID_DATE_PATTERN = "yyyy.MM.dd";
    private static final DateTimeFormatter VALID_DATE_FORMATTER = DateTimeFormat.forPattern(VALID_DATE_PATTERN);
    private static final String BIRTHDAY_DATE_PATTERN = "yyyy年MM月dd日";
    private static final DateTimeFormatter BIRTHDAY_DATE_FORMATTER = DateTimeFormat.forPattern(BIRTHDAY_DATE_PATTERN);

    private byte[] source;

    static {
        IdCardScan.addOcrServerIP(App.getInstance().getOcrServerHost(), App.getInstance().getOcrServerPort());
    }

    public IdCardRecognizer() {
    }

    public IdCardRecognizer(byte[] source) {
        this.source = source;
    }

    public byte[] getSource() {
        return source;
    }

    private String getFileName() {
        return RandomStringUtils.randomAlphanumeric(FILENAME_LENGTH);
    }

    private String getAbsoluteFileName() {
        return String.format(FILENAME_PATTERN, App.getInstance().getIdCardImageBase(), getFileName());
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

    public IdCard recognize(boolean isFront) {
        String fileName = getAbsoluteFileName();
        saveFile(fileName);
        IDCard card = IdCardScan.ocr(fileName);
        if (!isFront) {
            String[] vs = StringUtils.split(card.ValidPeriod, VALID_DATE_DELIMITER);
            return new IdCard(card.IssueAuthority, VALID_DATE_FORMATTER.parseDateTime(vs[0]).toDate(),
                    VALID_DATE_FORMATTER.parseDateTime(vs[1]).toDate());
        }
        Date birthday = BIRTHDAY_DATE_FORMATTER.parseDateTime(card.Birthday).toDate();
        return new IdCard(card.CardNo, card.Name, SexEnum.fromName(card.Sex), birthday, card.Folk, card.Address);
    }
}
