package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateDto;
import com.marcomarchionni.ibportfolio.services.parsers.ResponseParser;
import com.marcomarchionni.ibportfolio.services.validators.UpdateDtoValidator;
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

    @Override
    @Transactional
    public CombinedUpdateReport update(User user, FlexQueryResponseDto dto) {

        // Check if dto has the latest data
        LocalDate latestToDateInDb = flexStatementService.findLatestToDate(user);
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
        updateDtoValidator.isValid(updateDto);
        updateDtoValidator.hasValidAccountId(updateDto, user.getAccountId());

        // Update flex statement, positions, open dividends, closed dividends
        var flexStatementReport = flexStatementService.updateFlexStatements(user, updateDto.getFlexStatement());
        var positionReport = positionService.updatePositions(user, updateDto.getPositions());
        var tradeReport = tradeService.updateTrades(user, updateDto.getTrades());
        var dividendReport = dividendService.updateDividends(user, updateDto.getDividends());

        // Return report
        return CombinedUpdateReport.builder().flexStatements(flexStatementReport).trades(tradeReport)
                .positions(positionReport).dividends(dividendReport).build();
    }
}
