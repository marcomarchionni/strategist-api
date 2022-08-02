package com.marcomarchionni.ibportfolio.models.dtos.request;

import com.marcomarchionni.ibportfolio.models.validation.NullOrNotBlank;
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
