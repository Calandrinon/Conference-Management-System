package com.imps.cms.service;


import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    private static final String email = "nimdaCMS69420@gmail.com";
    private static final String username = "nimdaCMS69420@gmail.com";
    private static final String password = "wsbrurguryprwtmt";

    public boolean SendEmail(String title, String body, String destination) {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.email));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(destination)
            );
            message.setSubject(title);
            message.setText(body);

            Transport.send(message);


            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
}
