package com.rumorify.ws.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.requests.CredentialsRequest;
import com.rumorify.ws.dto.responses.GetUserByEmailResponse;
import com.rumorify.ws.exception.ResourceNotFoundException;
import com.rumorify.ws.exception.UserNotFoundException;
import com.rumorify.ws.model.Token;
import com.rumorify.ws.model.User;
import com.rumorify.ws.repository.TokenRepository;
import com.rumorify.ws.repository.UserRepository;
import com.rumorify.ws.service.TokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    public Token generateToken(GetUserByEmailResponse user, CredentialsRequest credentials) {
        String randomValue = UUID.randomUUID().toString();
        User inDb = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId()));
        Token token = new Token();
        token.setToken(randomValue);
        token.setUser(inDb);
        return tokenRepository.save(token);
    }

    @Override
    public int verifyToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            return 0;
        String token = authorizationHeader.split(" ")[1];
        return tokenRepository.findById(token).orElseThrow(() -> new ResourceNotFoundException()).getUser().getId();

    }

}
