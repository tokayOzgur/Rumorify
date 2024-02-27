package com.rumorify.ws.dto.responses;

import lombok.Data;

@Data
public class GetAllUserResponse {
    private int id;
    private String username;
    private String email;
    private boolean active;
    private String activationToken;
}
