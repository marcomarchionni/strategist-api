package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.validators.AssetCategory;
import com.marcomarchionni.strategistapi.validators.DateInterval;
import com.marcomarchionni.strategistapi.validators.NullOrNotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
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

    @Size(max = 5)
    @NullOrNotBlank
    private String symbol;

    @AssetCategory
    private String assetCategory;
}
