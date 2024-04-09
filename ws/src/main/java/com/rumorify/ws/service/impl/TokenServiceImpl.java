package com.rumorify.ws.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.requests.CredentialsRequest;
import com.rumorify.ws.dto.responses.GetUserByEmailResponse;
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

    // TODO: return user not id
    @Override
    public int verifyToken(String authorizationHeader) {
        var tokenInDb = getToken(authorizationHeader);
        if (!tokenInDb.isPresent())
            return 0;
        return tokenInDb.get().getUser().getId();
    }

    @Override
    public void logout(String authorizationHeader) {
        var tokenInDb = getToken(authorizationHeader);
        if (!tokenInDb.isPresent())
            return;
        tokenRepository.delete(tokenInDb.get());
    }

    private Optional<Token> getToken(String authorizationHeader) {
        if (authorizationHeader == null)
            return Optional.empty();
        String token = authorizationHeader.split(" ")[1];
        return tokenRepository.findById(token);
    }
}
