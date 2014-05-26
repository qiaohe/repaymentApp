package com.huayuan.domain.crawler.billparser;

/**
 * Created by dell on 14-6-5.
 */
public class BillEmailParserFactory {

    private static final BillEmailParserFactory INSTANCE = new BillEmailParserFactory();

    private BillEmailParserFactory() {
    }

    public static BillEmailParserFactory getInstance() {
        return INSTANCE;
    }
    /*
    VALUE	NAME
1	工商银行
2	农业银行
3	中国银行
4	建设银行
5	交通银行
6	光大银行
7	广发银行
8	华夏银行
9	民生银行
10	平安银行
11	兴业银行
12	招商银行
13	浦发银行
14	中信银行
15	邮储银行
16	上海银行
17	北京银行
     */

    public BillEmailParser create(final Integer bankId) {
        switch (bankId) {
            case 4:
                return new CcbEmailParser();
            case 7:
                return new CgbEmailParser();
            case 12:
                return new CgbEmailParser();
            default:
                throw new IllegalStateException("This bank can not be supported.");
        }
    }
}
