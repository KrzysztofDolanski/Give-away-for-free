package com.example.gaff.api_user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class GmailService implements MailService {

    private final MailConfiguration mailConfiguration;

    @Override
    public void sendEmail(Email email) {

        Session session = mailConfiguration.createSession();
        MimeMessage message = new MimeMessage(session);
        try {
            message.setText(email.getMailContent());
            message.setSubject(email.getMailSubject());
            message.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(email.getMailAddress())});
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();

        }

    }
}
