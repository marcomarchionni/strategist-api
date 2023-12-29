package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;

import java.util.Optional;

public interface FlexStatementAccessService {
    Optional<FlexStatement> findFirstOrderByToDateDesc();

    FlexStatement save(FlexStatement flexStatement);
}
