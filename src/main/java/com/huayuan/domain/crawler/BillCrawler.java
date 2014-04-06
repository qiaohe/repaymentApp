package com.huayuan.domain.crawler;

import com.huayuan.domain.member.CreditCardBill;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.search.SubjectTerm;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.Properties;

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
        BillDefinition bd = billDefinitions.getDefinitionBy(billEmail.getBank());
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Store store = session.getStore("pop3");
            store.connect(billEmail.getPop3(), billEmail.getEmail(), billEmail.getPassword());
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            for (Message message : folder.search(new SubjectTerm(bd.getTitle()))) {
                System.out.println(message.getSubject());
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new CreditCardBill();
    }
}
