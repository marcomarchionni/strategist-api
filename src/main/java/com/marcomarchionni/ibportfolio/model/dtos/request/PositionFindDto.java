package com.marcomarchionni.ibportfolio.model.dtos.request;

import com.marcomarchionni.ibportfolio.model.validation.AssetCategory;
import com.marcomarchionni.ibportfolio.model.validation.NullOrNotBlank;
import lombok.*;

import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionFindDto {

    private Boolean tagged;

    @Size(max = 20)
    @NullOrNotBlank
    private String symbol;

    @AssetCategory
    private String assetCategory;
}
