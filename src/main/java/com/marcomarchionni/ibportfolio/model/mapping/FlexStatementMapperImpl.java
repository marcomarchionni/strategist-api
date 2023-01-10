package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FlexStatementMapperImpl implements FlexStatementMapper {

    private final ModelMapper mapper;

    public FlexStatementMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public FlexStatement toFlexStatement(FlexQueryResponse.FlexStatement flexStatementDto) {
        return mapper.map(flexStatementDto, FlexStatement.class);
    }
}
