package com.rumorify.ws.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumorify.ws.dto.requests.CredentialsRequest;
import com.rumorify.ws.dto.responses.AuthResponse;
import com.rumorify.ws.service.AuthService;
import com.rumorify.ws.shared.GenericMessage;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

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

    @DeleteMapping
    GenericMessage logout(@RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
        authService.logout(authorizationHeader);
        return new GenericMessage("User logged out successfully!");
    }

}
