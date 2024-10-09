package com.marcomarchionni.strategistapi.dtos.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSummary {
    private String firstName;
    private String lastName;
    private String email;
    private String accountId;
}
