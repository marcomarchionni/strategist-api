package com.marcomarchionni.ibportfolio.dtos.request;

import com.marcomarchionni.ibportfolio.validators.PortfolioName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
