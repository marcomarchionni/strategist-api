package com.marcomarchionni.ibportfolio.dtos.request;

import com.marcomarchionni.ibportfolio.controllers.validators.PortfolioName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioCreateDto {

    @PortfolioName
    private String name;
}
