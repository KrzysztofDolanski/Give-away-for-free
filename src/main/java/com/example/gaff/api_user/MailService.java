package com.example.gaff.api_user;

import org.springframework.stereotype.Service;

@Service
public interface MailService {

    void sendEmail(Email email);
}
