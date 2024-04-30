package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.validators.EntityName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioSave implements EntitySave {
    private Long id;

    @Schema(description = "Creation date", example = "2021-01-01")
    private LocalDate createdAt;

    @EntityName
    @Schema(description = "Portfolio name must start with capital letter, contain 3-30 characters. Letters, numbers, " +
            "spaces, underscore and apostrophe allowed.", example = "Rule Makers")
    private String name;
    @Schema(description = "Portfolio description", example = "Portfolio containing Rule Makers stocks")
    private String description;
}
