package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.validators.EntityName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NameUpdate {

    @NotNull
    @Schema(description = "id", example = "1")
    private Long id;

    @EntityName
    @Schema(description = "Name must start with capital letter, contain 3-30 characters. Letters, numbers, " +
            "spaces, underscore and apostrophe allowed.", example = "Stock Long")
    private String name;
}
