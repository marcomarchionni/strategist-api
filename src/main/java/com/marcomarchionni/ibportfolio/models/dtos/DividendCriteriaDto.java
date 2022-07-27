package com.marcomarchionni.ibportfolio.models.dtos;

import com.marcomarchionni.ibportfolio.models.validation.DateInterval;
import com.marcomarchionni.ibportfolio.models.validation.NullOrNotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@DateInterval(dateFrom = "exDateFrom", dateTo = "exDateTo")
@DateInterval(dateFrom = "payDateFrom", dateTo = "payDateTo")
public class DividendCriteriaDto {

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
