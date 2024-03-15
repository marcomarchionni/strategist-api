package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Position;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.response.PositionSummary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PositionMapperImpl implements PositionMapper {

    ModelMapper modelMapper;

    public PositionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PositionSummary toPositionSummary(Position position) {
        return modelMapper.map(position, PositionSummary.class);
    }

    @Override
    public Position toPosition(FlexQueryResponseDto.OpenPosition positionDto) {
        return modelMapper.map(positionDto, Position.class);
    }

    @Override
    public Position mergeFlexProperties(Position source, Position target) {
        modelMapper.map(source, target);
        return target;
    }
}

