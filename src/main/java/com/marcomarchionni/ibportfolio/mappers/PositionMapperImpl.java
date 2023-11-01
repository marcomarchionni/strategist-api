package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionSummaryDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PositionMapperImpl implements PositionMapper {

    ModelMapper modelMapper;

    public PositionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PositionSummaryDto toPositionListDto(Position position) {
        return modelMapper.map(position, PositionSummaryDto.class);
    }

    @Override
    public Position toPosition(FlexQueryResponseDto.OpenPosition positionDto) {
        return modelMapper.map(positionDto, Position.class);
    }

    @Override
    public Position mergeIbProperties(Position source, Position target) {
        modelMapper.map(source, target);
        return target;
    }
}

