package by.ilyin.workexchange.util.email;

import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class EmailManager {

    private static final String SEND_FROM = "ilyin.testemail@gmail.com";
    private static final String SERVER_ADDRESS = "http://localhost:8080/workexchangeapp_war_exploded/controller/";

    private static Logger logger = LogManager.getLogger();

    public void sendActivationMail(String sendTo, String activationCode) {
        logger.debug("sendActivationMail() start sending");
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "465"); //587 tls //465
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        //activationCode = "!@#$%&*()_+?TmX3$esms+@jS%jJkF(1koX0Lb@VnilPH!uHLxprY*hti3Ak)*U+RGS&umip)V(xsFMEXqsZ+CEfsnu6eS35ib!R6j5GLwk4AiU3+Z9cxVz3Z(jzJH9@%^ZtBqkmNt";

        Authenticator auth = new EmailAuthenticator("ilyin.testemail", "mucwuudbmrbqvopu");
        Session session = Session.getDefaultInstance(properties, auth);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(SEND_FROM);
            mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(sendTo));
            mimeMessage.setSubject("it set subject test string");
            //String urlString = activationCode;
            //byte[] bytes = urlString.getBytes(StandardCharsets.UTF_8);
            //String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);
            //String encodedStr = URLEncoder.encode(urlString, Charset.forName("utf-8"));
            mimeMessage.setText(SERVER_ADDRESS + "?command=sign_up_activation&activation_code=" + activationCode);
            Transport.send(mimeMessage);
            logger.debug("activation email sent");
        } catch (MessagingException e) {
            logger.warn("error sending activation email");
            e.printStackTrace();
        }
    }

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
