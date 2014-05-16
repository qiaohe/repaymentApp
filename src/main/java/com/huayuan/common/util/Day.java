package com.huayuan.common.util;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by Johnson on 4/8/14.
 */
public final class Day {
    private static final String DEFAULT_PATTERN = "yyyy.MM.dd";
    public static final Day TODAY = new Day(new Date());
    private final Date date;

    public Day(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Date plusMonths(int months) {
        return new DateTime(date).plusMonths(months).toDate();
    }

    public Day valueOf(Date d) {
        return new Day(d);
    }

    public String toString() {
        return new DateTime(date).toString(DEFAULT_PATTERN);
    }

    public String toString(String pattern) {
        return new DateTime(date).toString(pattern);
    }
}
