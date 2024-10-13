package com.marcomarchionni.strategistapi.services.odata;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import org.springframework.data.jpa.domain.Specification;

public interface PortfolioSpecification {
    Specification<Portfolio> fromFilter(String filter, String accountId);
}
