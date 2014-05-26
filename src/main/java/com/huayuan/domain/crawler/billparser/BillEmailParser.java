package com.huayuan.domain.crawler.billparser;

/**
 * Created by dell on 14-6-5.
 */
public interface BillEmailParser {
    public BillValue parse(String content);
}
