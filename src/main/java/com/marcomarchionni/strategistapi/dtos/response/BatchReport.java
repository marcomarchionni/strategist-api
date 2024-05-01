package com.marcomarchionni.strategistapi.dtos.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BatchReport<R> {
    private List<R> created = new ArrayList<>();
    private List<R> updated = new ArrayList<>();
    private List<Long> deleted = new ArrayList<>();
}
