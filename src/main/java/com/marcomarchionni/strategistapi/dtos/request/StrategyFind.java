package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.validators.NullOrNotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyFind {

    @NullOrNotBlank
    private String name;
}
