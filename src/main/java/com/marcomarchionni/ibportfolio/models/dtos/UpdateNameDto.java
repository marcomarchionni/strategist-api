package com.marcomarchionni.ibportfolio.models.dtos;

import com.marcomarchionni.ibportfolio.models.validation.PortfolioName;
import lombok.*;

import javax.validation.constraints.NotNull;

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
