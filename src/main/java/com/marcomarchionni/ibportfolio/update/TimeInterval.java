package com.marcomarchionni.ibportfolio.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class TimeInterval {

    private LocalDate fromDate;
    private LocalDate toDate;
}
