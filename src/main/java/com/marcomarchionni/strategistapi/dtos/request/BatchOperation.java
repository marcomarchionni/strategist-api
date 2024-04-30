package com.marcomarchionni.strategistapi.dtos.request;

import lombok.Data;

@Data
public class BatchOperation<T> {
    private String method;
    private Long entityId;
    private T dto;
}
