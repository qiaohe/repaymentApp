package com.huayuan.common;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by Johnson on 4/8/14.
 */
public final class Day {
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
}
