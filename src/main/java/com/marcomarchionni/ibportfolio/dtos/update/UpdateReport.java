package com.marcomarchionni.ibportfolio.dtos.update;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
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
