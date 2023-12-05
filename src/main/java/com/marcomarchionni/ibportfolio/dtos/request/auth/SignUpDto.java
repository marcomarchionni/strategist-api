package com.marcomarchionni.ibportfolio.dtos.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
