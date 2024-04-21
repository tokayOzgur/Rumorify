package com.rumorify.ws.service.impl;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(TokenServiceImpl.class);

    @Override
    public Token generateToken(GetUserByEmailResponse user, CredentialsRequest credentials) {
        User inDb = userRepository.findById(user.getId()).orElseThrow(() -> {
            logger.error("User not found: " + user.getId());
            return new UserNotFoundException(user.getId());
        });
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

        logger.error("Token not found or expired: " + authorizationHeader);
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
        if (authorizationHeader == null) {
            logger.error("Authorization header is null");
            return Optional.empty();
        }
        String token = authorizationHeader.split(" ")[1];
        return tokenRepository.findById(token);
    }

    @Override
    public Token findToken(String cookieValue) {
        return tokenRepository.findById(cookieValue).orElseGet(() -> {
            logger.error("Token not found: " + cookieValue);
            return null;
        });
    }
}
