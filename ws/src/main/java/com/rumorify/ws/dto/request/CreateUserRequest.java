package com.rumorify.ws.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;
}
