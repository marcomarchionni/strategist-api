package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.response.update.CombinedUpdateReport;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateDto;
import com.marcomarchionni.strategistapi.services.parsers.ResponseParser;
import com.marcomarchionni.strategistapi.validators.UpdateDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UpdateServiceImpl implements UpdateService {

    private final ResponseParser parser;

    private final TradeService tradeService;

    private final DividendService dividendService;

    private final FlexStatementService flexStatementService;

    private final PositionService positionService;

    private final UpdateDtoValidator updateDtoValidator;

    private final UserService userService;

    @Override
    @Transactional
    public CombinedUpdateReport update(FlexQueryResponseDto dto) {

        // Check if dto has the latest data
        LocalDate latestToDateInDb = flexStatementService.findLatestToDate();
        LocalDate toDateInFlexQuery = dto.nullSafeGetFlexStatement().getToDate();
        boolean flexQueryHasTheLatestData = toDateInFlexQuery.isAfter(latestToDateInDb);

        // Parse dto
        UpdateDto updateDto;
        if (flexQueryHasTheLatestData) {
            updateDto = parser.parseAllData(dto);
        } else {
            updateDto = parser.parseHistoricalData(dto);
        }

        // Validate update dto
        String accountId = userService.getUserAccountId();
        updateDtoValidator.isValid(updateDto);
        updateDtoValidator.hasValidAccountId(updateDto, accountId);

        // Update flex statement, positions, open dividends, closed dividends
        var flexStatementReport = flexStatementService.updateFlexStatements(updateDto.getFlexStatement());
        var positionReport = positionService.updatePositions(updateDto.getPositions());
        var tradeReport = tradeService.updateTrades(updateDto.getTrades());
        var dividendReport = dividendService.updateDividends(updateDto.getDividends());

        // Return report
        return CombinedUpdateReport.builder().flexStatements(flexStatementReport).trades(tradeReport)
                .positions(positionReport).dividends(dividendReport).build();
    }
}
