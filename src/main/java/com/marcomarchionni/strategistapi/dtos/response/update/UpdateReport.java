package com.marcomarchionni.strategistapi.dtos.response.update;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class UpdateReport<T> {

    @Builder.Default
    private final List<T> added = List.of();
    @Builder.Default
    private final List<T> merged = List.of();
    @Builder.Default
    private final List<T> deleted = List.of();
    @Builder.Default
    private final List<T> skipped = List.of();
}
