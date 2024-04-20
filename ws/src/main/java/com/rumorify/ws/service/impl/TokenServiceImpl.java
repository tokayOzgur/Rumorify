package com.rumorify.ws.service.impl;

import java.time.OffsetDateTime;
import java.util.Optional;

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
        User inDb = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId()));
        Token token = new Token();
        token.setUser(inDb);
        token.setExpirationDate(OffsetDateTime.now().plusDays(1));
        return tokenRepository.save(token);
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        var tokenInDb = extractToken(authorizationHeader);
        if (tokenInDb.isPresent() && !tokenInDb.get().isExpired() && tokenInDb.get().isActive())
            return tokenInDb.get().getUser();
        return null;
    }

    @Override
    public void logout(String authorizationHeader) {
        var tokenInDb = extractToken(authorizationHeader);
        if (!tokenInDb.isPresent()) return;
        tokenInDb.get().setActive(false);
        tokenRepository.save(tokenInDb.get());
    }

    private Optional<Token> extractToken(String authorizationHeader) {
        if (authorizationHeader == null) return Optional.empty();
        String token = authorizationHeader.split(" ")[1];
        return tokenRepository.findById(token);
    }

    @Override
    public Token findToken(String cookieValue) {
        return tokenRepository.findById(cookieValue).orElse(null);
    }
}
