package com.example.gaff.api_user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

@Controller
@RequiredArgsConstructor
@AllArgsConstructor
public class GmailAuthenticator extends Authenticator {

    private java.lang.String username;
    private java.lang.String password;


    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

}
