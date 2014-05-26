package com.huayuan.domain.crawler;

import com.huayuan.common.util.Day;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by dell on 14-4-10.
 */

public final class EmailSearcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSearcher.class);
    private static final Properties PROPS = new Properties();
    private static final int STEP = 100;

    private BillEmail billEmail;

    public EmailSearcher(BillEmail billEmail) {
        this.billEmail = billEmail;
    }


    private Folder getFolder() throws MessagingException {
        Session session = Session.getDefaultInstance(PROPS, null);
        Store store = session.getStore("imap");
        store.connect(billEmail.getImap(), billEmail.getEmail(), billEmail.getPassword());
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        return folder;
    }

    private boolean isMatch(Message message, final String subject) throws MessagingException {
        return message.getSubject() != null && message.getSubject().contains(subject);
    }

    public String search(final String subject) {
        try {
            Folder folder = getFolder();
            int index = folder.getMessageCount();
            final Date date = Day.TODAY.plusMonths(-2);
            while (index > 0) {
                Message[] messages = folder.getMessages(index - STEP < 0 ? 1 : index - STEP, index);
                for (Message message : messages) {
                    if (message.getSentDate().before(date)) return null;
                    if (isMatch(message, subject)) return getContent(message);
                }
                index -= STEP;
            }
        } catch (MessagingException | IOException ex) {
            LOGGER.error(ex.getMessage());
        }
        return null;
    }

    private String getContent(Message message) throws IOException, MessagingException {
        if (message.getContent() instanceof MimeMultipart) {
            MimeMultipart part = (MimeMultipart) message.getContent();
            for (int i = 0; i < part.getCount(); i++) {
                System.out.println(part.getBodyPart(i).getContentType());
                if (part.getBodyPart(i).getContentType().contains("text/html")) {
                    return part.getBodyPart(i).getContent().toString();
                }
            }
        }
        return null;
    }
}