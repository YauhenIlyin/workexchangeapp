package by.ilyin.workexchange.util.email;

import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailManager {

    public void sendMail(String email) {
        //String recipientEmail
        String from = "ilyin.testemail@gmail.com";         // sender email
        //String to = "prog.jekylin@gmail.com";       // receiver email
        String to = email;
        String host = "smtp.gmail.com";            // mail server host

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "465"); //587 tls //465
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        //properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Authenticator auth = new EmailAuthenticator("ilyin.testemail", "1991testpass1");
        Session session = Session.getDefaultInstance(properties, auth);

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(from);
            mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject("it set subject test string");
            mimeMessage.setText("hello, it's test string of text");
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("success");


    }

}
