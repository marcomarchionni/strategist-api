package com.marcomarchionni.ibportfolio.errorhandling;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDetails {
    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;
    private long timeStamp;
}
