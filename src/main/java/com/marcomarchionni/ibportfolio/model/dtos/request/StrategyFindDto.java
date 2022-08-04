package com.marcomarchionni.ibportfolio.model.dtos.request;

import com.marcomarchionni.ibportfolio.model.validation.NullOrNotBlank;
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
