package com.rumorify.ws.service.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.rumorify.ws.config.RumorifyProperties;
import com.rumorify.ws.service.EmailService;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Service
public class EmailServiceImp implements EmailService {

    private JavaMailSenderImpl mailSender;
    Dotenv dotenv = Dotenv.load();

    @Autowired
    private RumorifyProperties rumorifyProp;

    @PostConstruct
    public void initialize() {
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost(rumorifyProp.getEmail().host());
        mailSender.setPort(rumorifyProp.getEmail().port());
        mailSender.setUsername(dotenv.get("RUMORIFY_EMAIL_USERNAME"));
        mailSender.setPassword(dotenv.get("RUMORIFY_EMAIL_PASSWORD"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
    }

    @Override
    public void sendActivationEmail(String email, String activationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(rumorifyProp.getEmail().from());
        mailMessage.setTo(email);
        mailMessage.setSubject("Rumorify Account Activation");
        mailMessage.setText("To activate your account, please click the following link: "
                + rumorifyProp.getClient().host() + "/activate?token=" + activationToken);

        this.mailSender.send(mailMessage);
    }

}
