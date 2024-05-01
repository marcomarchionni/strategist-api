package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.BatchOperation;
import com.marcomarchionni.strategistapi.dtos.request.EntitySave;
import com.marcomarchionni.strategistapi.dtos.response.BatchReport;
import com.marcomarchionni.strategistapi.services.resolvers.ServiceResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchOperationServiceImpl implements BatchOperationService {
    private final ServiceResolver serviceResolver;

    @Override
    public <T extends EntitySave, R> BatchReport<R> executeBatchOperations(List<BatchOperation<T>> operations) {
        var report = new BatchReport<R>();
        for (BatchOperation<T> operation : operations) {
            EntityService<T, R> service = serviceResolver.resolveService(operation.getServiceType());
            switch (operation.getMethod()) {
                case "POST":
                    report.getCreated().add(service.create(operation.getDto()));
                    break;
                case "PUT":
                    report.getUpdated().add(service.update(operation.getDto()));
                    break;
                case "DELETE":
                    service.deleteById(operation.getEntityId());
                    report.getDeleted().add(operation.getEntityId());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported method: " + operation.getMethod());
            }

        }
        return report;
    }
}
