package com.rumorify.ws.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.requests.CredentialsRequest;
import com.rumorify.ws.dto.responses.AuthResponse;
import com.rumorify.ws.dto.responses.GetUserByEmailResponse;
import com.rumorify.ws.exception.AuthenticationException;
import com.rumorify.ws.model.Token;
import com.rumorify.ws.service.AuthService;
import com.rumorify.ws.service.TokenService;
import com.rumorify.ws.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse authenticate(CredentialsRequest credentials) {
        GetUserByEmailResponse inDB = userService.findByEmail(credentials.email());
        if (!passwordEncoder.matches(credentials.password(), inDB.getPassword()) || inDB == null)
            throw new AuthenticationException();

        Token token = tokenService.generateToken(inDB, credentials);
        return AuthResponse.builder().token(token).user(inDB).build();
    }

}
