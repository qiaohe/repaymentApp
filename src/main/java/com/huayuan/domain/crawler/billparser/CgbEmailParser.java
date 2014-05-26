package com.huayuan.domain.crawler.billparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by dell on 14-6-5.
 */
public class CgbEmailParser implements BillEmailParser {
    @Override
    public BillValue parse(String content) {
        Document doc = Jsoup.parse(content);
        String[] result = new String[3];
        Elements es = doc.select("table[width=722px][height=238px] tbody tbody tr td");
        for (Element element : es) {
            System.out.println(element.text());
        }
        return new BillValue();
    }
}
