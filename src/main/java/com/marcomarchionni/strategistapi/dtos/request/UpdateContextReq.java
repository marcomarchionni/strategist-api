package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.validators.ValidUpdateContext;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@ValidUpdateContext
public class UpdateContextReq {
    public enum SourceType {
        FILE, SERVER, SAMPLEDATA
    }

    @NotNull
    @Schema(description = "Source type", example = "SAMPLEDATA")
    SourceType sourceType;

    @Schema(description = "File (ignored if sourceType is not FILE)")
    MultipartFile file;

    @Schema(description = "Query id (ignored if sourceType is not SERVER)")
    String queryId;

    @Schema(description = "Token (ignored if sourceType is not SERVER)")
    String token;
}
