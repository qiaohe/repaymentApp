package com.huayuan.domain.crawler;

import com.huayuan.common.App;
import com.huayuan.domain.crawler.billparser.BillEmailParser;
import com.huayuan.domain.crawler.billparser.BillEmailParserFactory;
import com.huayuan.domain.crawler.billparser.BillValue;
import com.huayuan.domain.member.CreditCardBill;
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
    @Inject
    private App app;
    private BillDefinitions billDefinitions;

    @PostConstruct
    private void init() throws IOException {
        ClassPathResource resource = new ClassPathResource(DEFINITION_PATH);
        billDefinitions = (BillDefinitions) jaxbMarshaller.unmarshal(new StreamSource(resource.getInputStream()));
    }

    public CreditCardBill crawl(BillEmail billEmail) {
        final BillDefinition bd = billDefinitions.getDefinitionBy(billEmail.getBank());
        final String content = new EmailSearcher(billEmail).search(bd.getTitle());
        BillEmailParser parser = BillEmailParserFactory.getInstance().create(app.getBankId(billEmail.getBank()));
        BillValue billValue = parser.parse(content);
        return CreditCardBill.valueOf(billValue, billEmail, app.getBankId(billEmail.getBank()));
    }
}
