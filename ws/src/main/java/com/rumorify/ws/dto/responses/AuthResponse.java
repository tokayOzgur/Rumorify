package com.rumorify.ws.dto.responses;


import com.rumorify.ws.model.Token;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    GetUserByEmailResponse user;
    Token token;

}
