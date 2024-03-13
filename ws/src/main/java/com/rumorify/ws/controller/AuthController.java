package com.rumorify.ws.controller;

import org.springframework.web.bind.annotation.RestController;

import com.rumorify.ws.dto.requests.CredentialsRequest;
import com.rumorify.ws.dto.responses.AuthResponse;
import com.rumorify.ws.service.AuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tokay
 *
 */
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    final AuthService authService;

    @PostMapping
    public AuthResponse handleAuthentication(@Valid @RequestBody CredentialsRequest credentials) {
        return authService.authenticate(credentials);
    }

}