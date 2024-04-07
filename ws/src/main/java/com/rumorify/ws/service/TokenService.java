package com.rumorify.ws.service;

import com.rumorify.ws.dto.requests.CredentialsRequest;
import com.rumorify.ws.dto.responses.GetUserByEmailResponse;
import com.rumorify.ws.model.Token;

public interface TokenService {
    public Token generateToken(GetUserByEmailResponse user, CredentialsRequest credentials);

    public int verifyToken(String authorizationHeader);
}
