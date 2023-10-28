package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FlexStatementMapperImpl implements FlexStatementMapper {

    private final ModelMapper mapper;

    public FlexStatementMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public FlexStatement toFlexStatement(FlexQueryResponseDto.FlexStatement flexStatementDto) {
        return mapper.map(flexStatementDto, FlexStatement.class);
    }
}
