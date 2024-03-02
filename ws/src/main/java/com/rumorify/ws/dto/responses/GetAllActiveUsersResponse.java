package com.rumorify.ws.dto.responses;

import lombok.Data;

@Data
public class GetAllActiveUsersResponse {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private String profileDescription;

}
