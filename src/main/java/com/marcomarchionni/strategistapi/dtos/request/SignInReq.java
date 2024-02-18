package com.marcomarchionni.strategistapi.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInReq {
    @Email
    @Schema(description = "Email", example = "joe.smith@gmail.com")
    private String email;

    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Schema(description = "Password", example = "password")
    private String password;
}
