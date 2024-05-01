package com.marcomarchionni.strategistapi.services.parsers;

import com.marcomarchionni.strategistapi.dtos.request.BatchOperation;
import com.marcomarchionni.strategistapi.dtos.request.EntitySave;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

public interface BatchRequestParser {
    <T extends EntitySave> List<BatchOperation<T>> parseRequest(HttpServletRequest request, Class<T> dtoClass) throws IOException;
}
