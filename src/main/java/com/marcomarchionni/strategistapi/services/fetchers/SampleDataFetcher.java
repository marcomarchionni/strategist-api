package com.marcomarchionni.strategistapi.services.fetchers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.request.UpdateContextDto;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.SampleDataFileNotAvailableException;
import com.marcomarchionni.strategistapi.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;

@Component

public class SampleDataFetcher implements DataFetcher {

    private final String path;
    private final XmlMapper xmlMapper;
    private final UserService userService;
    private final ResourceLoader resourceLoader;

    public SampleDataFetcher(@Value("${sample.data.file.path}") String path,
                             XmlMapper xmlMapper,
                             UserService userService,
                             ResourceLoader resourceLoader) {
        this.path = path;
        this.xmlMapper = xmlMapper;
        this.userService = userService;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public FlexQueryResponseDto fetch(UpdateContextDto context) {

        try (InputStream stream = resourceLoader.getResource(path).getInputStream()) {
            // Read data from the sample xml file
            var dto = xmlMapper.readValue(stream, FlexQueryResponseDto.class);
            // Replace the accountId in the dto with the one from the authenticated user
            String accountId = userService.getUserAccountId();
            replaceAccountId(dto, accountId);
            return dto;
        } catch (Exception e) {
            throw new SampleDataFileNotAvailableException(e);
        }
    }

    private void replaceAccountId(FlexQueryResponseDto dto, String accountId) {

        var optionalFlexStatement = Optional.ofNullable(dto.getFlexStatements())
                .map(FlexQueryResponseDto.FlexStatements::getFlexStatement);

        optionalFlexStatement.ifPresent(flexStatement -> flexStatement.setAccountId(accountId));

        optionalFlexStatement.map(FlexQueryResponseDto.FlexStatement::getAccountInformation)
                .ifPresent(accountInformation -> accountInformation.setAccountId(accountId));

        dto.nullSafeGetOpenPositions().forEach(openPosition -> openPosition.setAccountId(accountId));
        dto.nullSafeGetOrders().forEach(order -> order.setAccountId(accountId));
        dto.nullSafeGetTrades().forEach(trade -> trade.setAccountId(accountId));
        dto.nullSafeGetChangeInDividendAccruals()
                .forEach(changeInDividendAccrual -> changeInDividendAccrual.setAccountId(accountId));
        dto.nullSafeGetOpenDividendAccruals()
                .forEach(openDividendAccrual -> openDividendAccrual.setAccountId(accountId));
    }
}
