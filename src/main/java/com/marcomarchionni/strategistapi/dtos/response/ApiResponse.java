package com.marcomarchionni.strategistapi.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiResponse<T> {
    private final List<T> result;
    private final long count;
}
