package com.rumorify.ws.dto.responses;

import lombok.Data;

@Data
public class GetUserByEmailResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
