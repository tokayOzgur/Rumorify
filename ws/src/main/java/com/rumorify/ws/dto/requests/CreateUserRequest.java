package com.rumorify.ws.dto.requests;

import com.rumorify.ws.validation.userValidation.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank(message = "{rumorify.constraints.username.notblank}")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "{rumorify.constraints.email.notblank}")
    @Email(message = "{rumorify.constraints.email.invalid}")
    @UniqueEmail
    private String email;

    @Size(min = 8, max = 50, message = "{rumorify.constraints.password.size}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "{rumorify.constraints.password.pattern}")
    private String password;
}
