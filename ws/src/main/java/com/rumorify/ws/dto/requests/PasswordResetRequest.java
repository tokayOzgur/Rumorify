package com.rumorify.ws.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordResetRequest {
    @NotNull
    @NotEmpty
    @NotBlank(message = "{rumorify.constraints.email.notblank}")
    @Email(message = "{rumorify.constraints.email.invalid}")
    private String email;
}
