package com.rumorify.ws.dto.responses;

import lombok.Data;

@Data
public class GetAllActiveUsersResponse {
    private int id;
    private String username;
    private String email;
}
