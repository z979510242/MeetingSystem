package csu.software.meetingsystem.emailCode;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


public class EmailCode {

    private final String myEmailAccount = "cse_meetingOrder@163.com";//发送的邮箱
    private final String myEmailPassword = "JQFNSLMNZJYOGLYX";
    private String receiveMailAccount = null ;
    private String info=null;

    public void setReceiveMailAccount(String receiveMailAccount) {
        this.receiveMailAccount = receiveMailAccount;
    }
    public void setInfo(String info) {
        this.info = info;
    }

    private  String myEmailSMTPServer = "smtp.163.com";

    public  void Send(String name) throws Exception {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", myEmailSMTPServer);
        props.setProperty("mail.smtp.auth", "true");

        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        Session session = Session.getDefaultInstance(props);
//        session.setDebug(true);
        MimeMessage message = createMessage(session, myEmailAccount, receiveMailAccount,info,name);
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public MimeMessage createMessage(Session session, String sendMail, String receiveMail, String info, String name) throws Exception {

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "会议室管理系统", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, name, "UTF-8"));
        message.setSubject("【会议室管理系统】邮箱验证码", "UTF-8");
        message.setContent("您的验证码是:"+info+",该验证码5分钟内有效，请勿泄露于他人！", "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
}
