package com.example.gaff.api_user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    private String mailAddress;
    private String mailSubject;
    private String mailContent;
    private boolean sent;

    public Email(String mailAddress, String mailSubject, String mailContent) {
        this.mailAddress = mailAddress;
        this.mailSubject = mailSubject;
        this.mailContent = mailContent;
        sent = false;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public String getMailContent() {
        return mailContent;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

}
