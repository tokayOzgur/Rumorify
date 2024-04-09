package com.rumorify.ws.service;

import com.rumorify.ws.dto.requests.CredentialsRequest;
import com.rumorify.ws.dto.responses.AuthResponse;

/**
 * @author tokay
 */
public interface AuthService {

    AuthResponse authenticate(CredentialsRequest credentials);

    void logout(String authorizationHeader);

    AuthResponse getCurrentUser(String cookieValue);

}
