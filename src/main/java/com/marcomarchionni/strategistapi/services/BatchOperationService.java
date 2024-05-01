package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.BatchOperation;
import com.marcomarchionni.strategistapi.dtos.request.EntitySave;
import com.marcomarchionni.strategistapi.dtos.response.BatchReport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BatchOperationService {
    <T extends EntitySave, R> BatchReport<R> executeBatchOperations(List<BatchOperation<T>> operations);
}
