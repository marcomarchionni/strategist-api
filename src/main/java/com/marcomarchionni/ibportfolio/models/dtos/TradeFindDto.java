package com.marcomarchionni.ibportfolio.models.dtos;

import com.marcomarchionni.ibportfolio.models.validation.AssetCategory;
import com.marcomarchionni.ibportfolio.models.validation.DateInterval;
import com.marcomarchionni.ibportfolio.models.validation.NullOrNotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DateInterval(dateFrom = "tradeDateFrom", dateTo = "tradeDateTo")
public class TradeFindDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tradeDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tradeDateTo;

    private Boolean tagged;

    @Size(max = 20)
    @NullOrNotBlank
    private String symbol;

    @AssetCategory
    private String assetCategory;
}
