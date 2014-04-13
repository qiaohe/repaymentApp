package com.huayuan.domain.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by dell on 14-4-10.
 */
public class BillEmailParser {
    private final BillDefinition billDefinition;

    public BillEmailParser(BillDefinition billDefinition) {
        this.billDefinition = billDefinition;
    }

    public String[] parse(String content) {
        Document doc = Jsoup.parse(content);
        String[] result = new String[3];
        Elements es = doc.select("table table table table").first().select("tbody tr td:nth-child(2n)");
        result[0] = es.get(1).text();
        result[1] = es.get(2).text();
        Elements es1 = doc.select("table table table table").get(1).select("tbody tr td:nth-child(2)");
        result[2] = es1.get(1).text();
        return result;
    }
}
