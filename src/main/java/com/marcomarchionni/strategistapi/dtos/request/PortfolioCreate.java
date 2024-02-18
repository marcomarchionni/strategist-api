package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.validators.PortfolioName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioCreate {

    @PortfolioName
    private String name;
}
