package com.rumorify.ws.service;

public interface EmailService {

    public void sendTokenEmail(String email, String token, int templateId);
}
