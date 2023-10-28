package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.PositionListDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PositionMapperImpl implements PositionMapper {

    ModelMapper modelMapper;

    public PositionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PositionListDto toPositionListDto(Position position) {
        return modelMapper.map(position, PositionListDto.class);
    }

    @Override
    public Position toPosition(FlexQueryResponseDto.OpenPosition positionDto) {
        return modelMapper.map(positionDto, Position.class);
    }
}

