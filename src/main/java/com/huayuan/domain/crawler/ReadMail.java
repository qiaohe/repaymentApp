package com.huayuan.domain.crawler;


import com.huayuan.domain.Bill;
import com.huayuan.domain.BillMailbox;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author li jiwang
 */
public class ReadMail {

    private MimeMessage mimeMessage = null;
    private String saveAttachPath = ""; // 附件下载后的存放目录
    private StringBuffer bodyText = new StringBuffer(); // 存放邮件内容的StringBuffer对象
    private String dateFormat = "yy-MM-dd HH:mm"; // 默认的日前显示格式

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadMail.class);

    /**
     * 构造函数,初始化一个MimeMessage对象
     */
    public ReadMail() {
    }

    public ReadMail(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    /**
     * read email bill
     *
     * @param billMailbox
     * @param readEmail
     * @return
     * @throws Exception
     */
    public Set<Bill> readBill(BillMailbox billMailbox, String readEmail) throws Exception {

        String userEmail = billMailbox.getEmail();
        String password = billMailbox.getPassword();

        String host = "pop." + userEmail.substring(userEmail.indexOf("@") + 1, userEmail.length());

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        Store store = session.getStore("pop3");
        store.connect(host, userEmail, password);

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Message message[] = folder.getMessages();

        Set<Bill> bills = new HashSet<Bill>();
        for (int i = 0; i < message.length; i++) {
            setMimeMessage((MimeMessage) message[i]);
            InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
            if (address[0].getAddress().equals(readEmail)) {
                getMailContent((Part) message[i]);
                Bill bill = parser(getBodyText());
                bills.add(bill);
            }
        }
        return bills;
    }

    /**
     * parser Mail
     *
     * @param tableStr
     */
    public Bill parser(String tableStr) {
        Bill bill = new Bill();
        Document doc = Jsoup.parse(tableStr);
        try {
            Elements tdElements = doc.select("table tr td font");
            System.out.println(tdElements.size());
            for (int i = 0; i < tdElements.size(); i++) {
                String text = tdElements.get(i).text();
                //Payment Due Date
                if (text.indexOf("Payment Due Date") >= 0) {
                    String payDue = tdElements.get(i).parent().parent().child(1).text();
                    if (!payDue.isEmpty()) ;
//                        bill.setPayDue(DateUtil.parseTimestamp(payDue));
                }
                //AMT_RMB
                if (text.indexOf("Cash Advance Limit") >= 0) {
                    String amtRmb = tdElements.get(i).parent().parent().child(1).text();
                    if (!amtRmb.isEmpty())
                        bill.setAmtRmb(Double.parseDouble(amtRmb.replaceAll(",", "")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bill;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    /**
     * 　*　获得发件人的地址和姓名
     */
    public String getFrom() throws Exception {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
        String from = address[0].getAddress();
        if (from == null) {
            from = "";
            LOGGER.error("无法知道发送者.");
        }
        String personal = address[0].getPersonal();

        if (personal == null) {
            personal = "";
            LOGGER.error("无法知道发送者的姓名.");
        }

        String fromAddr = null;
        if (personal != null || from != null) {
            fromAddr = personal + "<" + from + ">";
            LOGGER.info("发送者是：" + fromAddr);
        } else {
            LOGGER.info("无法获得发送者信息.");
        }
        return fromAddr;
    }

    /**
     * 　*　获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同
     * 　*　"to"----收件人　"cc"---抄送人地址　"bcc"---密送人地址
     */
    public String getMailAddress(String type) throws Exception {
        String mailAddr = "";
        String addType = type.toUpperCase();

        InternetAddress[] address = null;
        if (addType.equals("TO") || addType.equals("CC")
                || addType.equals("BCC")) {

            if (addType.equals("TO")) {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(Message.RecipientType.TO);
            } else if (addType.equals("CC")) {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(Message.RecipientType.CC);
            } else {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(Message.RecipientType.BCC);
            }

            if (address != null) {
                for (int i = 0; i < address.length; i++) {
                    String emailAddr = address[i].getAddress();
                    if (emailAddr == null) {
                        emailAddr = "";
                    } else {
                        emailAddr = MimeUtility.decodeText(emailAddr);
                    }
                    String personal = address[i].getPersonal();
                    if (personal == null) {
                        personal = "";
                    } else {
                        personal = MimeUtility.decodeText(personal);
                    }
                    String compositeto = personal + "<" + emailAddr + ">";
                    mailAddr += "," + compositeto;
                }
                mailAddr = mailAddr.substring(1);
            }
        } else {
            throw new Exception("错误的电子邮件类型!");
        }
        return mailAddr;
    }

    /**
     * 　*　获得邮件主题
     */
    public String getSubject() throws MessagingException {
        String subject = "";
        try {
            subject = MimeUtility.decodeText(mimeMessage.getSubject());
            if (subject == null) {
                subject = "";
            }
        } catch (Exception exce) {
            exce.printStackTrace();
        }
        return subject;
    }

    /**
     * 　*　获得邮件发送日期
     */
    public String getSentDate() throws Exception {
        Date sentDate = mimeMessage.getSentDate();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        String strSentDate = format.format(sentDate);
        return strSentDate;
    }

    /**
     * 　*　获得邮件正文内容
     */
    public String getBodyText() {
        return bodyText.toString();
    }

    /**
     * 　　*　解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
     * 　　*　主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
     */

    public void getMailContent(Part part) throws Exception {

        String contentType = part.getContentType();
        // 获得邮件的MimeType类型

        int nameIndex = contentType.indexOf("name");

        boolean conName = false;

        if (nameIndex != -1) {
            conName = true;
        }


        if (part.isMimeType("text/plain") && conName == false) {
            // text/plain 类型
            bodyText.append((String) part.getContent());
        } else if (part.isMimeType("text/html") && conName == false) {
            // text/html 类型
            bodyText.append((String) part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            // multipart/*
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getMailContent(multipart.getBodyPart(i));
            }
        } else if (part.isMimeType("message/rfc822")) {
            // message/rfc822
            getMailContent((Part) part.getContent());
        } else {

        }
    }

    /**
     * 　　*　判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
     */
    public boolean getReplySign() throws MessagingException {

        boolean replySign = false;

        String needReply[] = mimeMessage
                .getHeader("Disposition-Notification-To");

        if (needReply != null) {
            replySign = true;
        }
        if (replySign) {
            LOGGER.info("该邮件需要回复");
        } else {
            LOGGER.info("该邮件不需要回复");
        }
        return replySign;
    }

    /**
     * 　获得此邮件的Message-ID
     */
    public String getMessageId() throws MessagingException {
        String messageID = mimeMessage.getMessageID();
        return messageID;
    }

    /**
     * 判断此邮件是否已读，如果未读返回false,反之返回true
     */
    public boolean isNew() throws MessagingException {
        boolean isNew = false;
        Flags flags = ((Message) mimeMessage).getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == Flags.Flag.SEEN) {
                isNew = true;
                System.out.println("seen email...");
                // break;
            }
        }
        return isNew;
    }

    /**
     * 判断此邮件是否包含附件
     */
    public boolean isContainAttach(Part part) throws Exception {
        boolean attachFlag = false;
        // String contentType = part.getContentType();
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mPart = mp.getBodyPart(i);
                String disposition = mPart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                        .equals(Part.INLINE))))
                    attachFlag = true;
                else if (mPart.isMimeType("multipart/*")) {
                    attachFlag = isContainAttach((Part) mPart);
                } else {
                    String conType = mPart.getContentType();

                    if (conType.toLowerCase().indexOf("application") != -1)
                        attachFlag = true;
                    if (conType.toLowerCase().indexOf("name") != -1)
                        attachFlag = true;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            attachFlag = isContainAttach((Part) part.getContent());
        }
        return attachFlag;
    }

    /**
     * 　*　保存附件
     */

    public void saveAttachMent(Part part) throws Exception {
        String fileName = "";
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mPart = mp.getBodyPart(i);
                String disposition = mPart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                        .equals(Part.INLINE)))) {
                    fileName = mPart.getFileName();
                    if (fileName.toLowerCase().indexOf("gb2312") != -1) {
                        fileName = MimeUtility.decodeText(fileName);
                    }
                    saveFile(fileName, mPart.getInputStream());
                } else if (mPart.isMimeType("multipart/*")) {
                    saveAttachMent(mPart);
                } else {
                    fileName = mPart.getFileName();
                    if ((fileName != null)
                            && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
                        fileName = MimeUtility.decodeText(fileName);
                        saveFile(fileName, mPart.getInputStream());
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachMent((Part) part.getContent());
        }
    }

    /**
     * 　*　设置日期显示格式
     */
    public void setDateFormat(String format) throws Exception {
        this.dateFormat = format;
    }

    /**
     * 　*　获得附件存放路径
     */
    public String getAttachPath() {
        return saveAttachPath;
    }

    /**
     * 　设置附件存放路径
     */
    public void setAttachPath(String attachPath) {
        this.saveAttachPath = attachPath;
    }

    /**
     * 　*　真正的保存附件到指定目录里
     */
    private void saveFile(String fileName, InputStream in) throws Exception {
        String osName = System.getProperty("os.name");
        String storeDir = getAttachPath();
        String separator = "";
        if (osName == null) {
            osName = "";
        }
        if (osName.toLowerCase().indexOf("win") != -1) {
            separator = "\\";
            if (storeDir == null || storeDir.equals(""))
                storeDir = "c:\\tmp";
        } else {
            separator = "/";
            storeDir = "/tmp";
        }
        File storeFile = new File(storeDir + separator + fileName);
        // for(int　i=0;storefile.exists();i++){
        // storefile　=　new　File(storedir+separator+fileName+i);
        // }
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;

        try {
            bos = new BufferedOutputStream(new FileOutputStream(storeFile));
            bis = new BufferedInputStream(in);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("文件保存失败!");
        } finally {
            bos.close();
            bis.close();
        }
    }
}
