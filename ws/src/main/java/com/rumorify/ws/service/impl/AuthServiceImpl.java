package com.rumorify.ws.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.requests.CredentialsRequest;
import com.rumorify.ws.dto.responses.AuthResponse;
import com.rumorify.ws.dto.responses.GetUserByEmailResponse;
import com.rumorify.ws.dto.responses.GetUserByIdResponse;
import com.rumorify.ws.exception.AuthenticationException;
import com.rumorify.ws.model.Token;
import com.rumorify.ws.service.AuthService;
import com.rumorify.ws.service.ModelMapperService;
import com.rumorify.ws.service.TokenService;
import com.rumorify.ws.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapperService mapper;

    @Override
    public AuthResponse authenticate(CredentialsRequest credentials) {
        GetUserByEmailResponse inDB = userService.findByEmail(credentials.email());

        if (inDB == null || !passwordEncoder.matches(credentials.password(), inDB.getPassword()))
            throw new AuthenticationException();

        if (!inDB.isActive() || inDB.isDeleted())
            throw new AuthenticationException();

        GetUserByIdResponse userResp = mapper.forResponse().map(inDB, GetUserByIdResponse.class);
        Token token = tokenService.generateToken(inDB, credentials);
        return AuthResponse.builder().token(token).user(userResp).build();
    }

    @Override
    public void logout(String authorizationHeader) {
        tokenService.logout(authorizationHeader);
    }

    @Override
    public AuthResponse getCurrentUser(String cookieValue) {
        Token token = tokenService.findToken(cookieValue);
        if (token == null)
            throw new AuthenticationException();

        GetUserByIdResponse userResp = mapper.forResponse().map(token.getUser(), GetUserByIdResponse.class);
        return AuthResponse.builder().user(userResp).build();
    }

}
