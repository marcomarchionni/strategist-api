package com.marcomarchionni.strategistapi.dtos.request.auth;

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
public class SignUpDto {
    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;
    @NotNull
    @Size(min = 8, max = 20)
    private String lastName;
    @Email
    private String email;
    @NotNull
    @Size(min = 8, max = 20)
    private String password;
    @NotNull
    @Size(min = 8, max = 8)
    private String accountId;
}
