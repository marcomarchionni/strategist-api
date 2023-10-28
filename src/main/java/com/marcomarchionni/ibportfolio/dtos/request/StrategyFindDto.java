package com.marcomarchionni.ibportfolio.dtos.request;

import com.marcomarchionni.ibportfolio.controllers.validators.NullOrNotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyFindDto {

    @NullOrNotBlank
    private String name;
}
