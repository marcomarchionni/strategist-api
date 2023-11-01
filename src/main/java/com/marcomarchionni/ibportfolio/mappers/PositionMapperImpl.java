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
        Position p = new Position();
        p.setId(positionDto.getConid());
        p.setConId(positionDto.getConid());
        p.setReportDate(positionDto.getReportDate());
        p.setSymbol(positionDto.getSymbol());
        p.setDescription(positionDto.getDescription());
        p.setAssetCategory(positionDto.getAssetCategory());
        p.setPutCall(positionDto.getPutCall());
        p.setStrike(positionDto.getStrike());
        p.setExpiry(positionDto.getExpiry());
        p.setQuantity(positionDto.getPosition());
        p.setCostBasisPrice(positionDto.getCostBasisPrice());
        p.setCostBasisMoney(positionDto.getCostBasisMoney());
        p.setMarkPrice(positionDto.getMarkPrice());
        p.setMultiplier(positionDto.getMultiplier());
        p.setPositionValue(positionDto.getPositionValue());
        p.setFifoPnlUnrealized(positionDto.getFifoPnlUnrealized());
        return p;
    }

    @Override
    public Position mergeIbProperties(Position source, Position target) {
        target.setId(source.getId());
        target.setConId(source.getConId());
        target.setReportDate(source.getReportDate());
        target.setSymbol(source.getSymbol());
        target.setDescription(source.getDescription());
        target.setAssetCategory(source.getAssetCategory());
        target.setPutCall(source.getPutCall());
        target.setStrike(source.getStrike());
        target.setExpiry(source.getExpiry());
        target.setQuantity(source.getQuantity());
        target.setCostBasisPrice(source.getCostBasisPrice());
        target.setCostBasisMoney(source.getCostBasisMoney());
        target.setMarkPrice(source.getMarkPrice());
        target.setMultiplier(source.getMultiplier());
        target.setPositionValue(source.getPositionValue());
        target.setFifoPnlUnrealized(source.getFifoPnlUnrealized());
        return target;
    }
}

