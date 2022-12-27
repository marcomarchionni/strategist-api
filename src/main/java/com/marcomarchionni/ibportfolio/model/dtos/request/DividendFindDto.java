package com.marcomarchionni.ibportfolio.model.dtos.request;

import com.marcomarchionni.ibportfolio.model.validation.DateInterval;
import com.marcomarchionni.ibportfolio.model.validation.NullOrNotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@DateInterval(dateFrom = "exDateFrom", dateTo = "exDateTo")
@DateInterval(dateFrom = "payDateFrom", dateTo = "payDateTo")
public class DividendFindDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate exDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate exDateTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate payDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate payDateTo;

    private Boolean tagged;

    @NullOrNotBlank
    @Size(max=20)
    private String symbol;
}
