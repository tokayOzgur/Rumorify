package com.rumorify.ws.service.impl;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.requests.CredentialsRequest;
import com.rumorify.ws.dto.responses.GetUserByEmailResponse;
import com.rumorify.ws.model.User;
import com.rumorify.ws.service.TokenService;
import com.rumorify.ws.token.Token;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public Token generateToken(GetUserByEmailResponse user, CredentialsRequest credentials) {
        String token = Base64.getEncoder()
                .encodeToString((credentials.email() + ":" + credentials.password()).getBytes());
        return new Token("Basic", token);
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyToken'");
    }

}
