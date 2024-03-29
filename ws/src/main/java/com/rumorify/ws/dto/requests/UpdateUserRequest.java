package com.rumorify.ws.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank
    @NotEmpty
    @Size(min = 6, max = 20)
    private String username;
    @NotBlank
    @NotEmpty
    @Size(min = 6, max = 20)
    private String firstName;
    @NotBlank
    @NotEmpty
    @Size(min = 6, max = 20)
    private String lastName;
    @NotBlank
    @NotEmpty
    @Size(min = 6, max = 150)
    private String profileDescription;
    @NotBlank
    @NotEmpty
    private String image;
}
