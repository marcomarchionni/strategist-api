package com.marcomarchionni.strategistapi.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpReq {
    @NotNull
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    @Schema(description = "First name", example = "Joe")
    private String firstName;

    @NotNull
    @Size(min = 8, max = 20, message = "Last name must be between 8 and 20 characters")
    @Schema(description = "Last name", example = "Smith")
    private String lastName;

    @Email
    @Schema(description = "Email", example = "joe.smith@gmail.com")
    private String email;

    @NotNull
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Schema(description = "Password", example = "password")
    private String password;

    @NotNull
    @Size(min = 8, max = 8, message = "Account ID must be 8 characters")
    @Schema(description = "Account ID", example = "U1234567")
    private String accountId;
}
