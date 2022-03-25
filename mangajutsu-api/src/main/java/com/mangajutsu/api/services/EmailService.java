package com.mangajutsu.api.services;

import javax.mail.MessagingException;

import com.mangajutsu.api.emails.AbstractEmailContext;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendMail(AbstractEmailContext email) throws MessagingException;
}