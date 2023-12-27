package com.marcomarchionni.ibportfolio.dtos.update;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.services.validators.ValidAccountId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@ValidAccountId
public class UpdateDto {
    @NotNull
    private FlexStatement flexStatement;
    @NotNull
    private List<Trade> trades;
    @NotNull
    private List<Position> positions;
    @NotNull
    private List<Dividend> dividends;
}
