package com.huayuan.domain.crawler;

import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.SortTerm;
import org.joda.time.DateTime;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.search.*;
import java.util.Date;
import java.util.Properties;

/**
 * Created by dell on 14-4-6.
 */
public class Test {
    public static void main(String[] args) {


        Session session = Session.getDefaultInstance(System.getProperties(), null);
        try {
//            Store store = session.getStore("pop3");
//            store.connect("pop3.163.com", "tusc_heqiao@163.com", "Forest2003");

            IMAPStore store = (IMAPStore) session.getStore("imaps");
            store.connect("imap.163.com", 993, "tusc_heqiao@163.com", "Forest2003");
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
//            SearchTerm term = new AndTerm(new FromStringTerm(username),
//                    new SubjectTerm(srcMail.getSubject()));
//            SearchTerm st = new AndTerm(new SentDateTerm(ComparisonTerm.GE, new Date()), new SentDateTerm(ComparisonTerm.LE, new DateTime(2014,4,5,0,0,0,0,0,0)));

            ReceivedDateTerm dateTerm = new ReceivedDateTerm(ComparisonTerm.EQ, new Date());

            Message[] messages = folder.search(dateTerm);
            for (Message message : messages) {
                System.out.println(message.getSentDate());
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
