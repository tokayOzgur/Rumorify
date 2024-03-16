package com.rumorify.ws.service.impl;

import java.util.Base64;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rumorify.ws.dto.requests.CredentialsRequest;
import com.rumorify.ws.dto.responses.GetUserByEmailResponse;
import com.rumorify.ws.service.TokenService;
import com.rumorify.ws.service.UserService;
import com.rumorify.ws.token.Token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final UserService tokenService;

    @Override
    public Token generateToken(GetUserByEmailResponse user, CredentialsRequest credentials) {
        String token = Base64.getEncoder()
                .encodeToString((credentials.email() + ":" + credentials.password()).getBytes());
        return new Token("Basic", token);
    }

    @Override
    public int verifyToken(String authorizationHeader) {
        if (authorizationHeader == null)
            return 0;
        String base64encoded = authorizationHeader.split("Basic ")[1];
        String decoded = new String(Base64.getDecoder().decode(base64encoded));
        CredentialsRequest credentials = new CredentialsRequest(decoded.split(":")[0], decoded.split(":")[1]);
        GetUserByEmailResponse inDB = tokenService.findByEmail(credentials.email());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (inDB != null && passwordEncoder.matches(credentials.password(), inDB.getPassword()))
            return inDB.getId();
        return 0;
    }

}
