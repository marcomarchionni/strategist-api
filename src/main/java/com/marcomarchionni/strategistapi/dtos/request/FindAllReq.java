package com.marcomarchionni.strategistapi.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FindAllReq {

    @Builder.Default
    private int skip = 0;

    @Builder.Default
    private int top = 10;

    private String orderBy;

    // Simple filter parameters
    private String name;
    private String description;
    private String createdAfter;
    private String createdBefore;
}