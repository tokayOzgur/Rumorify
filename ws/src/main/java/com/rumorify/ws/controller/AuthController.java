package com.rumorify.ws.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
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
    private final AuthService authService;

    // TODO: projeden Authorization ve localStorage çıkarılacak
    @PostMapping
    public ResponseEntity<AuthResponse> handleAuthentication(@Valid @RequestBody CredentialsRequest credentials) {
        AuthResponse authResponse = authService.authenticate(credentials);
        ResponseCookie cookie = ResponseCookie.from("rumor-token", authResponse.getToken().getToken())
                .path("/").sameSite("None").secure(true).httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
    }

    @DeleteMapping
    public ResponseEntity<GenericMessage> logout(
            @RequestHeader(name = "Authorization", required = false) String authorizationHeader,
            @CookieValue(name = "rumor-token", required = false) String cookieValue) {

        System.err.println("********************" + cookieValue);
        var tokenWithPrefix = authorizationHeader;
        if (cookieValue != null && !cookieValue.isEmpty())
            tokenWithPrefix = "AnyPrefix " + cookieValue;

        ResponseCookie cookie = ResponseCookie.from("rumor-token", "")
                .path("/").httpOnly(true).maxAge(0).build();
        authService.logout(tokenWithPrefix);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new GenericMessage("User logged out successfully!"));
    }

}
