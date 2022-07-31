package com.marcomarchionni.ibportfolio.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateNameDto {

    @NotNull
    private Long id;

    @NotBlank
    private String name;
}
