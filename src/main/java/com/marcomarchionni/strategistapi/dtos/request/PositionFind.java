package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.validators.AssetCategory;
import com.marcomarchionni.strategistapi.validators.NullOrNotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionFind {

    private Boolean tagged;

    @Size(max = 20)
    @NullOrNotBlank
    private String symbol;

    @AssetCategory
    private String assetCategory;
}
