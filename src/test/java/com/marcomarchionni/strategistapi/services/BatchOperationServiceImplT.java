package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.BatchOperation;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.repositories.PortfolioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("classpath:/dbScripts/insertSampleData.sql")
class BatchOperationServiceImplIT {

    @Autowired
    BatchOperationService batchOperationService;

    @Autowired
    PortfolioRepository portfolioRepository;


    @Test
    void executeBatchOperations() {
        // given
        var saverPortfolioId = portfolioRepository.findByAccountIdAndName("U1111111", "Saver Portfolio").get().getId();
        var optionPortfolioId = portfolioRepository.findByAccountIdAndName("U1111111", "Option Portfolio").get()
                .getId();

        var operation1 = BatchOperation.<PortfolioSave>builder()
                .serviceType(EntityService.ServiceType.PORTFOLIO)
                .method("POST")
                .dto(PortfolioSave.builder().createdAt(LocalDate.now()).name("Portfolio 1")
                        .description("Portfolio 1 description").build())
                .build();
        var operation2 = BatchOperation.<PortfolioSave>builder()
                .serviceType(EntityService.ServiceType.PORTFOLIO)
                .method("PUT")
                .entityId(saverPortfolioId)
                .dto(PortfolioSave.builder().id(saverPortfolioId).createdAt(LocalDate.now()).name("Saver Portfolio 2")
                        .description("Saver Portfolio 2 description").build())
                .build();
        var operation3 = BatchOperation.<PortfolioSave>builder()
                .serviceType(EntityService.ServiceType.PORTFOLIO)
                .method("DELETE")
                .entityId(optionPortfolioId)
                .build();
        List<BatchOperation<PortfolioSave>> operations = List.of(operation1, operation2, operation3);

        // when
        var report = batchOperationService.executeBatchOperations(operations);

        // then
        System.out.println(report);
        assertEquals(1, report.getCreated().size());
        assertEquals(1, report.getUpdated().size());
        assertEquals(1, report.getDeleted().size());

    }

    @Test
    void executeBatchOperationsPartialSuccess() {
        // TODO: perform valid operations, notify failed operations
    }

}