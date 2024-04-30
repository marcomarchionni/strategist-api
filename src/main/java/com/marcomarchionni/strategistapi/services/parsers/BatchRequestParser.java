package com.marcomarchionni.strategistapi.services.parsers;

import com.marcomarchionni.strategistapi.dtos.request.BatchOperation;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

public interface BatchRequestParser {
    <T> List<BatchOperation<T>> parseRequest(HttpServletRequest request, Class<T> dtoClass) throws IOException;
}
