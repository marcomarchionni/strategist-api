package com.marcomarchionni.ibportfolio.services.fetchers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.request.UpdateContextDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.SampleDataFileNotAvailableException;
import com.marcomarchionni.ibportfolio.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SampleDataFetcher implements DataFetcher {

    private static final String PATH = "classpath:flex/Flex.xml";
    private final XmlMapper xmlMapper;
    private final UserService userService;
    private final ResourceLoader resourceLoader;

    @Override
    public FlexQueryResponseDto fetch(UpdateContextDto context) {

        try (InputStream stream = resourceLoader.getResource(PATH).getInputStream()) {
            // Read data from the sample xml file
            var dto = xmlMapper.readValue(stream, FlexQueryResponseDto.class);
            // Replace the accountId in the dto with the one from the authenticated user
            String accountId = userService.getUserAccountId();
            replaceAccountId(dto, accountId);
            return dto;
        } catch (Exception e) {
            throw new SampleDataFileNotAvailableException();
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
