package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.validators.DateInterval;
import com.marcomarchionni.strategistapi.validators.NullOrNotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@DateInterval(dateFrom = "exDateAfter", dateTo = "exDateBefore")
@DateInterval(dateFrom = "payDateAfter", dateTo = "payDateBefore")
public class DividendFind {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate exDateAfter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate exDateBefore;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate payDateAfter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate payDateBefore;

    private Boolean tagged;

    @NullOrNotBlank
    @Size(max=20)
    private String symbol;
}
