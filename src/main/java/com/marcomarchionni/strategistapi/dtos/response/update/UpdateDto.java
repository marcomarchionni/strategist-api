package com.marcomarchionni.strategistapi.dtos.response.update;

import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.domain.FlexStatement;
import com.marcomarchionni.strategistapi.domain.Position;
import com.marcomarchionni.strategistapi.domain.Trade;
import com.marcomarchionni.strategistapi.validators.ValidAccountId;
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
