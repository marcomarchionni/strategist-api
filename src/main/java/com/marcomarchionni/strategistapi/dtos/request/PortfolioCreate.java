package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.validators.EntityName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioCreate {

    @EntityName
    @Schema(description = "Portfolio name must start with capital letter, contain 3-30 characters. Letters, numbers, " +
            "spaces, underscore and apostrophe allowed.", example = "Rule Makers")
    private String name;
}
