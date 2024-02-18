package com.rumorify.ws.service;

public interface EmailService {

    public void sendActivationEmail(String email, String activationToken);
}
