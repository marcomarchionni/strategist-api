package com.marcomarchionni.strategistapi.dtos.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
  private final List<T> result;
  private final long count;
}
