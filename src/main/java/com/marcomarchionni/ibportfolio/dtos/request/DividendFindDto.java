package com.marcomarchionni.ibportfolio.dtos.request;

import com.marcomarchionni.ibportfolio.validators.DateInterval;
import com.marcomarchionni.ibportfolio.validators.NullOrNotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
