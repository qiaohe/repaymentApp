package com.huayuan.common.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

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

    public int escapeDays(Date date) {
        return Days.daysBetween(new DateTime(date).withTime(0, 0, 0, 0), new DateTime().withTime(0, 0, 0, 0)).getDays();
    }

    public String toString() {
        return new DateTime(date).toString(DEFAULT_PATTERN);
    }

    public String toString(String pattern) {
        return new DateTime(date).toString(pattern);
    }

    public Date getCurrentDayOfMonth() {
        int months = Months.monthsBetween(new DateTime(date), new DateTime()).getMonths();
        return new DateTime(date).plusMonths(months + 1).toDate();
    }

    public Date plusDays(Integer days) {
        return new DateTime(date).plusDays(days).toDate();
    }

    public Date nextMonth() {
        return new DateTime(date).plusMonths(1).toDate();
    }

    public boolean isSameDay(Date d) {
        DateTime d1 = new DateTime(date);
        DateTime d2 = new DateTime(d);
        return d1.getYear() == d2.getYear() && d1.getMonthOfYear() == d2.getMonthOfYear() && d1.getDayOfMonth() == d2.getDayOfMonth();
    }
}
