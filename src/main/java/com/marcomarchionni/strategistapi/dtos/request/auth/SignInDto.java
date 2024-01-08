package com.marcomarchionni.strategistapi.dtos.request.auth;

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
public class SignInDto {
    @Email
    private String email;
    @Size(min = 8, max = 20)
    private String password;
}
