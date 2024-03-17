package com.rumorify.ws.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank
    @NotEmpty
    private String username;
    @NotBlank
    @NotEmpty
    private String firstName;
    @NotBlank
    @NotEmpty
    private String lastName;
    @NotBlank
    @NotEmpty
    private String profileDescription;
}
