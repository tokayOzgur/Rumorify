package com.rumorify.ws.service;

import com.rumorify.ws.dto.requests.CredentialsRequest;
import com.rumorify.ws.dto.responses.GetUserByEmailResponse;
import com.rumorify.ws.model.User;
import com.rumorify.ws.token.Token;

public interface TokenService {
    public Token generateToken(GetUserByEmailResponse user, CredentialsRequest credentials);

    public User verifyToken(String authorizationHeader);
}