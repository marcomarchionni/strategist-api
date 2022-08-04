package com.marcomarchionni.ibportfolio.model.dtos.request;

import com.marcomarchionni.ibportfolio.model.validation.AssetCategory;
import com.marcomarchionni.ibportfolio.model.validation.DateInterval;
import com.marcomarchionni.ibportfolio.model.validation.NullOrNotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
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
