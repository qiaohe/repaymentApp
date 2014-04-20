package com.huayuan.domain.crawler;

import com.huayuan.domain.member.BillSourceEnum;
import com.huayuan.domain.member.CreditCardBill;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

/**
 * Created by dell on 14-3-21.
 */
@Component(value = "billCrawler")
public class BillCrawler {
    private static final String DEFINITION_PATH = "billDefinition.xml";
    @Inject
    private Unmarshaller jaxbMarshaller;
    private BillDefinitions billDefinitions;

    @PostConstruct
    private void init() throws IOException {
        ClassPathResource resource = new ClassPathResource(DEFINITION_PATH);
        billDefinitions = (BillDefinitions) jaxbMarshaller.unmarshal(new StreamSource(resource.getInputStream()));
    }

    public CreditCardBill crawl(BillEmail billEmail) {
        final BillDefinition bd = billDefinitions.getDefinitionBy(billEmail.getBank());
        final String content = new EmailSearcher(billEmail).search(bd.getTitle());
        String[] items = new BillEmailParser(bd).parse(content);
        CreditCardBill result = new CreditCardBill();
        result.setEmail(billEmail.getEmail());
        result.setSource(BillSourceEnum.CRAWLER);
        result.setBank((short) 1);
        result.setAmtRmb(55000d);
        result.setCrl(55000l);
        result.setCycleFrom(new DateTime(2014, 4, 11, 0, 0, 0).toDate());
        result.setCycleFrom(new DateTime(2014, 5, 11, 0, 0, 0).toDate());
        return result;
    }

    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("E:\\development\\working\\repaymentApp\\repaymentApp\\src\\main\\resources\\applicationContext.xml");
        BillCrawler crawler = context.getBean("billCrawler", BillCrawler.class);
        long start = System.currentTimeMillis();
        crawler.crawl(new BillEmail("tusc_heqiao@163.com", "Forest2003", 13));
        System.out.println((System.currentTimeMillis() - start) / 1000);
        System.out.println("ending");
    }
}
