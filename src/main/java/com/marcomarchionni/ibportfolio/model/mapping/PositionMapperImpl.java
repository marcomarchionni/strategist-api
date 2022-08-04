package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.Position;
import com.marcomarchionni.ibportfolio.model.dtos.response.PositionListDto;
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
}
