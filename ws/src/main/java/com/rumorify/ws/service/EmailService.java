package com.rumorify.ws.service;

import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {
    public void sendActivationEmail(String to, String subject, String text);

    public JavaMailSender getJavaMailSender();
}
