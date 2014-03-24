package com.huayuan.domain.crawler;

import com.huayuan.domain.Bill;
import com.huayuan.domain.BillMailbox;

import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

/**
 * Created by dell on 14-3-21.
 */
public class BillCrawler {

    /**
     *
     * @param billMailbox
     * @return
     */
    public Bill crawl(BillMailbox billMailbox) {

        return new Bill();
    }
}
