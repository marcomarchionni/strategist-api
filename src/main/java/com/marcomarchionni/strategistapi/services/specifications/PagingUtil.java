package com.marcomarchionni.strategistapi.services.specifications;

import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFindAllReq;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PagingUtil {

  public static Pageable createPageable(FindAllReq findReq) {
    return createPageable(findReq.getSkip(), findReq.getTop(), findReq.getOrderBy());
  }

  public static Pageable createPageable(StrategyFindAllReq findReq) {
    return createPageable(findReq.getSkip(), findReq.getTop(), findReq.getOrderBy());
  }

  private static Pageable createPageable(int skip, int top, String orderBy) {
    // Calculate the page number from skip and top values
    int pageNumber = skip / top;

    if (orderBy == null || orderBy.isBlank()) {
      // Return Pageable object without sorting
      return PageRequest.of(pageNumber, top);
    } else {
      // Check if orderBy is provided, and split into field and direction
      String[] sortParams = orderBy.split(" ");
      String sortField = sortParams[0]; // e.g., "name"
      Sort.Direction direction = Sort.Direction.ASC;
      // Set sorting direction if provided, otherwise default to ASC
      if (sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])) {
        direction = Sort.Direction.DESC;
      }
      // Return Pageable object with sorting
      return PageRequest.of(pageNumber, top, Sort.by(direction, sortField));
    }
  }
}
