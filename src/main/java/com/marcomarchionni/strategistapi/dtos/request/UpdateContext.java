package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.validators.ValidUpdateContext;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidUpdateContext
public class UpdateContext {
    @NotNull(message = "Source type is required")
    @Schema(description = "Allowed values: SERVER (fetch data from Interactive Brokers WebFlexService), " +
            "FILE (fetch data from a flex query xml file), SAMPLEDATA (fetch sample data for testing " +
            "purposes)", example = "SAMPLEDATA")
    SourceType sourceType;
    @Schema(description = "Query id (ignored if sourceType is not SERVER)")
    String queryId;
    @Schema(description = "Token (ignored if sourceType is not SERVER)")
    String token;
    @Schema(name = "file", description = "File (ignored if sourceType is not FILE)", type = "string", format = "binary")
    MultipartFile file;

    public enum SourceType {
        FILE, SERVER, SAMPLEDATA
    }
}
