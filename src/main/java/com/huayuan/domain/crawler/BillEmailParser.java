package com.huayuan.domain.crawler;

import com.huayuan.domain.member.CreditCardBill;

/**
 * Created by dell on 14-4-10.
 */
public class BillEmailParser {
    private final BillDefinition billDefinition;

    public BillEmailParser(BillDefinition billDefinition) {
        this.billDefinition = billDefinition;
    }

    public CreditCardBill parse(String content) {
       return null;
    }

}
