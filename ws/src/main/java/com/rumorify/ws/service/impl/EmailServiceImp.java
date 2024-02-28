package com.rumorify.ws.service.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.rumorify.ws.config.RumorifyProperties;
import com.rumorify.ws.exception.ActivationNotificationException;
import com.rumorify.ws.service.EmailService;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImp implements EmailService {

    private JavaMailSenderImpl mailSender;
    Dotenv dotenv = Dotenv.load();

    @Autowired
    private RumorifyProperties rumorifyProp;

    // TODO: Replace this with a proper template engine
    String activationEmail = """
            <html>
                <body>
                    <h1>Activate your account</h1>
                    <p>To activate your account, please click the following link:</p>
                    <a href="${url}">Activate</a>
                </body>
            </html>
            """;

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
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            String activationUrl = rumorifyProp.getClient().host() + "/activation/" + activationToken;
            String mailText = activationEmail.replace("${url}", activationUrl);
            // TODO: check variables
            mailMessage.setFrom(rumorifyProp.getEmail().from());
            mailMessage.setTo(email);
            mailMessage.setSubject("Rumorify Account Activation");
            mailMessage.setText(mailText, true);
        } catch (MessagingException e) {
            // TODO: fix this error handling
            throw new ActivationNotificationException();
        }

        this.mailSender.send(mimeMessage);
    }

}
