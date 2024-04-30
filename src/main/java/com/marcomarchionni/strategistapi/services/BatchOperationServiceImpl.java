package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.BatchOperation;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchOperationServiceImpl implements BatchOperationService {
    private final PortfolioService portfolioService;

    @Override
    public <T> void executeBatchOperation(List<BatchOperation<T>> operations) {
        for (BatchOperation<T> operation : operations) {
            switch (operation.getMethod()) {
                case "POST":
                    saveEntity(operation.getDto());
                    break;
                case "PUT":
                    updateEntity(operation.getDto());
                    break;
                case "DELETE":
                    deleteEntity(operation.getEntityId(), operation.getDto());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported method: " + operation.getMethod());
            }

        }
    }

    private <T> void saveEntity(T dto) {
        if (dto instanceof PortfolioSave) {
            portfolioService.create((PortfolioSave) dto);
        }
    }

    private <T> void updateEntity(T dto) {
        if (dto instanceof PortfolioSave) {
            // TODO: Implement update method in PortfolioService
            portfolioService.create((PortfolioSave) dto);
        }
    }

    private <T> void deleteEntity(Long entityId, T dto) {
        if (dto instanceof PortfolioSave) {
            portfolioService.deleteById(entityId);
        }
    }
}
