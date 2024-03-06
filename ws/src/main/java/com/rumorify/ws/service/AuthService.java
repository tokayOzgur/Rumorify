package com.rumorify.ws.service;

import com.rumorify.ws.dto.requests.CredentialsRequest;

/**
 * @author tokay
 */
public interface AuthService {

    void authenticate(CredentialsRequest credentials);
    
}
