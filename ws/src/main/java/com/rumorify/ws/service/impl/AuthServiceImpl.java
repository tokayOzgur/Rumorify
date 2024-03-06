package com.rumorify.ws.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.requests.CredentialsRequest;
import com.rumorify.ws.dto.responses.GetUserByEmailResponse;
import com.rumorify.ws.exception.AuthenticationException;
import com.rumorify.ws.service.AuthService;
import com.rumorify.ws.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    @Override
    public void authenticate(CredentialsRequest credentials) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        GetUserByEmailResponse inDB = userService.findByEmail(credentials.email());
        if (!passwordEncoder.matches(credentials.password(), inDB.getPassword())) {
            throw new AuthenticationException();
        }

    }

}
