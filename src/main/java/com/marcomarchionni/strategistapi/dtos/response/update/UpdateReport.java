package com.marcomarchionni.strategistapi.dtos.response.update;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UpdateReport<T> {

  @Builder.Default private final List<T> added = List.of();
  @Builder.Default private final List<T> merged = List.of();
  @Builder.Default private final List<T> deleted = List.of();
  @Builder.Default private final List<T> skipped = List.of();
}
