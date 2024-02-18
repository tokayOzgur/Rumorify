package com.rumorify.ws.dto.responses;

import lombok.Data;

@Data
public class GetUserByUserNameResponse {
    private int id;
    private String username;
    private String email;
    private String password;
}
