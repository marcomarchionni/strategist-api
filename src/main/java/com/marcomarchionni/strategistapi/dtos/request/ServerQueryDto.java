package com.marcomarchionni.strategistapi.dtos.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServerQueryDto {
    @NotNull
    private String token;
    @NotNull
    private String queryId;
}