package com.marcomarchionni.strategistapi.services.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcomarchionni.strategistapi.dtos.request.BatchOperation;
import com.marcomarchionni.strategistapi.dtos.request.EntitySave;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchRequestParserImpl implements BatchRequestParser {
    private static final String newChangeSetDeclaration = "boundary=changeset";
    private static final String startOperation = "Content-Type: application/http";
    private final ObjectMapper mapper;

    @Override
    public <T extends EntitySave> List<BatchOperation<T>> parseRequest(HttpServletRequest request, Class<T> dtoClass) throws IOException {

        List<BatchOperation<T>> operations = new ArrayList<>();
        BatchOperation<T> currentOperation = null;
        String changeSetBoundary = null;
        String line;

        String batchBoundary = extractBoundary(request.getContentType());
        log.info("Batch boundary: {}", batchBoundary);
        Stage stage = Stage.SEARCH_BATCH_BOUNDARY;

        var reader = new BufferedReader(new InputStreamReader(request.getInputStream()));

        while ((line = reader.readLine()) != null) {
            log.info("Line: {}, Stage: {}", line, stage);
            if (stage == Stage.SEARCH_BATCH_BOUNDARY) {
                if (line.contains(batchBoundary)) {
                    stage = Stage.SEARCH_CHANGE_SET_BOUNDARY_DECLARATION;
                    continue;
                }
            }
            if (stage == Stage.SEARCH_CHANGE_SET_BOUNDARY_DECLARATION) {
                if (line.contains(newChangeSetDeclaration)) {
                    changeSetBoundary = extractBoundary(line);
                    stage = Stage.SEARCH_CHANGE_SET_BOUNDARY;
                    continue;
                }
            }
            if (stage == Stage.SEARCH_CHANGE_SET_BOUNDARY) {
                if (line.contains(changeSetBoundary)) {
                    stage = Stage.SEARCH_OPERATION;
                    continue;
                }
            }
            if (stage == Stage.SEARCH_OPERATION) {
                if (line.startsWith(startOperation)) {
                    currentOperation = new BatchOperation<>();
                    stage = Stage.PARSE_OPERATION_ACTION;
                    continue;
                }
            }
            if (stage == Stage.PARSE_OPERATION_ACTION) {
                if (line.startsWith("POST") || line.startsWith("PUT") || line.startsWith("DELETE")) {
                    parseOperationLine(currentOperation, line);
                    stage = Stage.PARSE_OPERATION_JSON;
                    continue;
                }
            }
            if (stage == Stage.PARSE_OPERATION_JSON) {
                if (line.startsWith("{") || line.startsWith("[")) {
                    T dto = mapper.readValue(line, dtoClass);
                    currentOperation.setDto(dto);
                    continue;
                } else if (line.contains(changeSetBoundary + "--")) {
                    operations.add(currentOperation);
                    currentOperation = null;
                    stage = Stage.SEARCH_END_BATCH_BOUNDARY;
                    continue;
                } else if (line.contains(changeSetBoundary)) {
                    operations.add(currentOperation);
                    currentOperation = null;
                    stage = Stage.SEARCH_OPERATION;
                    continue;
                }
            }
            if (stage == Stage.SEARCH_END_BATCH_BOUNDARY) {
                if (line.contains(batchBoundary + "--")) {
                    stage = Stage.END;
                }
            }
        }
        if (stage != Stage.END) {
            throw new IllegalArgumentException("Invalid batch request: unexpected end of request");
        }

        return operations;
    }

    private <T extends EntitySave> void parseOperationLine(BatchOperation<T> operation, String line) {
        String[] parts = line.split(" ");
        String method = parts[0];
        operation.setMethod(method); // POST, PUT, DELETE

        if (method.equals("PUT") || method.equals("DELETE")) {
            // Extract entity id from parts[1]
            Matcher matcher = Pattern.compile("\\((\\d+)\\)").matcher(parts[1]);
            if (matcher.find()) {
                operation.setEntityId(Long.parseLong(matcher.group(1)));
            } else throw new IllegalArgumentException("Invalid " + method + " operation: entity id not found");
        }
    }

    private String extractBoundary(String line) {
        String[] parts = line.split(";");
        for (String part : parts) {
            String partTrimmed = part.trim();
            if (partTrimmed.startsWith("boundary=")) {
                return "--" + partTrimmed.substring("boundary=".length());
            }
        }
        throw new IllegalArgumentException("Invalid batch request: boundary not found");
    }

    enum Stage {
        SEARCH_BATCH_BOUNDARY,
        SEARCH_CHANGE_SET_BOUNDARY_DECLARATION,
        SEARCH_CHANGE_SET_BOUNDARY,
        SEARCH_OPERATION,
        PARSE_OPERATION_ACTION,
        PARSE_OPERATION_JSON,
        SEARCH_END_BATCH_BOUNDARY,
        END
    }
}
