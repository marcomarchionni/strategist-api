package com.marcomarchionni.ibportfolio.model.dtos.request;

import com.marcomarchionni.ibportfolio.model.validation.PortfolioName;
import lombok.*;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNameDto {

    @NotNull
    private Long id;

    @PortfolioName
    private String name;
}
