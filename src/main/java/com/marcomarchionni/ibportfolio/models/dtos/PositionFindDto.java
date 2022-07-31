package com.marcomarchionni.ibportfolio.models.dtos;

import com.marcomarchionni.ibportfolio.models.validation.AssetCategory;
import com.marcomarchionni.ibportfolio.models.validation.NullOrNotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class PositionFindDto {

    private Boolean tagged;

    @Size(max = 20)
    @NullOrNotBlank
    private String symbol;

    @AssetCategory
    private String assetCategory;
}
