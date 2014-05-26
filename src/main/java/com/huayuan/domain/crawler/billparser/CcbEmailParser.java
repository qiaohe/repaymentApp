package com.huayuan.domain.crawler.billparser;

import com.huayuan.common.util.Day;
import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by dell on 14-6-5.
 */
public class CcbEmailParser implements BillEmailParser {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String AMT_PATTERN = "[CNY,\\,,\\s]";
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern(DATE_PATTERN);

    @Override
    public BillValue parse(String content) {
        BillValue result = new BillValue();
        Document doc = Jsoup.parse(content);
        Elements es = doc.select("table table table table").first().select("tbody tr td:nth-child(2n)");
        result.setPayDue(FORMATTER.parseDateTime(es.get(1).text()).toDate());
        result.setCycleThru(FORMATTER.parseDateTime(es.get(0).text()).toDate());
        result.setCycleFrom(new Day(result.getCycleThru()).plusMonths(-1));
        result.setCrl(Double.valueOf(es.get(2).text().replaceAll(AMT_PATTERN, StringUtils.EMPTY)));
        Elements es1 = doc.select("table table table table").get(1).select("tbody tr td:nth-child(2)");
        result.setAmtRmb(Double.valueOf(es1.get(1).text()));
        return result;
    }

    public static void main(String[] args) {
        System.out.println("CNY 55,000".replaceAll("[CNY,\\,,\\s]", ""));
    }
}
