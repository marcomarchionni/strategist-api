package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.BatchOperation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BatchOperationService {
    <T> void executeBatchOperation(List<BatchOperation<T>> operations);
}
