package com.huayuan.domain.crawler;

import com.huayuan.domain.member.CreditCardBill;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

/**
 * Created by dell on 14-4-10.
 */
public class BillEmailParser {
    private final BillDefinition billDefinition;

    public BillEmailParser(BillDefinition billDefinition) {
        this.billDefinition = billDefinition;
    }

    public CreditCardBill parse(String content) throws IOException {
        File input = new File("c:/a.html");
        Document doc = Jsoup.parse(input, "UTF-8");
        for(Element element : doc.getElementsMatchingOwnText("信用额度\\s*</font>")) {
            System.out.println(element.toString());
        }


        return null;
    }

    public static void main(String[] args) throws IOException {
        new BillEmailParser(null).parse(null);
    }

}
