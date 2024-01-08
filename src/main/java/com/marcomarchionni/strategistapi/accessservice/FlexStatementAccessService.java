package com.marcomarchionni.strategistapi.accessservice;

import com.marcomarchionni.strategistapi.domain.FlexStatement;

import java.util.Optional;

public interface FlexStatementAccessService {
    Optional<FlexStatement> findFirstOrderByToDateDesc();

    FlexStatement save(FlexStatement flexStatement);
}
