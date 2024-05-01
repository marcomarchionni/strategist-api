package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.BatchOperation;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.services.resolvers.ServiceResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchOperationServiceImplTest {

    @Mock
    ServiceResolver serviceResolver;

    @Mock
    PortfolioServiceImpl portfolioServiceImpl;

    @Test
    void executeBatchOperations() {
        var operation1 = BatchOperation.<PortfolioSave>builder()
                .serviceType(EntityService.ServiceType.PORTFOLIO)
                .method("POST")
                .dto(PortfolioSave.builder().createdAt(LocalDate.now()).name("Portfolio 1")
                        .description("Portfolio 1 description").build())
                .build();
        var operation2 = BatchOperation.<PortfolioSave>builder()
                .serviceType(EntityService.ServiceType.PORTFOLIO)
                .method("PUT")
                .entityId(1L)
                .dto(PortfolioSave.builder().createdAt(LocalDate.now()).name("Portfolio 2")
                        .description("Portfolio 2 description").build())
                .build();
        var operation3 = BatchOperation.<PortfolioSave>builder()
                .serviceType(EntityService.ServiceType.PORTFOLIO)
                .method("DELETE")
                .entityId(2L)
                .build();
        List<BatchOperation<PortfolioSave>> operations = List.of(operation1, operation2, operation3);

        //when
        when(serviceResolver.<PortfolioSave, PortfolioSummary>resolveService(EntityService.ServiceType.PORTFOLIO)).thenReturn(portfolioServiceImpl);
        when(portfolioServiceImpl.create(operation1.getDto())).thenReturn(PortfolioSummary.builder().id(3L)
                .createdAt(operation1.getDto().getCreatedAt()).name(operation1.getDto().getName())
                .description(operation1.getDto().getDescription()).build());
        when(portfolioServiceImpl.update(operation2.getDto())).thenReturn(PortfolioSummary.builder().id(2L)
                .createdAt(operation2.getDto().getCreatedAt()).name(operation2.getDto().getName())
                .description(operation2.getDto().getDescription()).build());

        var batchOperationService = new BatchOperationServiceImpl(serviceResolver);

        //then
        var report = batchOperationService.executeBatchOperations(operations);
        System.out.println(report);
        assertEquals(1, report.getCreated().size());
        assertEquals(1, report.getUpdated().size());
        assertEquals(1, report.getDeleted().size());
    }


}