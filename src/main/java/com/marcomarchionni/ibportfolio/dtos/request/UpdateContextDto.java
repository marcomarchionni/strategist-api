package com.marcomarchionni.ibportfolio.dtos.request;

import com.marcomarchionni.ibportfolio.dtos.validators.ValidUpdateContext;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@ValidUpdateContext
public class UpdateContextDto {
    public enum SourceType {
        FILE, SERVER, SAMPLEDATA
    }

    @NotNull
    SourceType sourceType;
    MultipartFile file;
    String queryId;
    String token;
}
