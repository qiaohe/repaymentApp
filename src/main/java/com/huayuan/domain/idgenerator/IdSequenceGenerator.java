package com.huayuan.domain.idgenerator;

import com.huayuan.repository.IdSequenceRepository;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Johnson on 4/4/14.
 */
@Component(value = "idSequenceGenerator")
@Transactional
public class IdSequenceGenerator {
    public static final long NOT_FOUND = 0;
    private static final long STEP = 100;
    private static final ConcurrentHashMap<String, Long> NAME_VALUES_MAP = new ConcurrentHashMap<>();

    @Inject
    private IdSequenceRepository idSequenceRepository;

    private long getValue(final String name) {
        if (!NAME_VALUES_MAP.containsKey(name)) {
            NAME_VALUES_MAP.put(name, -1l);
        }
        return NAME_VALUES_MAP.get(name);
    }

    public synchronized long nextVal(final String name) {
        long value = getValue(name);
        if (value < 0) {
            value = idSequenceRepository.findOne(name).getNextValue();
            if (value <= NOT_FOUND) {
                value = -1;
            }
            idSequenceRepository.save(new IdSequence(name, value + STEP));
        }
        value++;
        if (value % STEP == 0) {
            idSequenceRepository.save(new IdSequence(name, value + STEP));
        }
        NAME_VALUES_MAP.put(name, value);
        return value;
    }

    public synchronized String nextValAsString(final String name) {
        return String.valueOf(nextVal(name));
    }

    public String getApplicationNo() {
        DateTime today = new DateTime();
        return MessageFormat.format("{0}10{1}", today.toString("yyyyMMdd"), StringUtils.leftPad(nextValAsString("APPL"), 6, "0"));
    }

    public String getStaffNo() {
        return StringUtils.leftPad(nextValAsString("STAFF"), 10, "0");
    }
}
