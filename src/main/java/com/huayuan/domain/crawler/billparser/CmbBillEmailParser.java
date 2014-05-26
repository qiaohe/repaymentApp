package com.huayuan.domain.crawler.billparser;

/**
 * Created by dell on 14-6-5.
 */
public class CmbBillEmailParser implements BillEmailParser {

    @Override
    public BillValue parse(String content) {
        return new BillValue();
    }
}
