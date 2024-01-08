package com.marcomarchionni.strategistapi.dtos.flex;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "FlexStatementResponse")
public class FlexStatementResponseDto implements FlexResponse {

    @JsonProperty("Status")
    private String status;

    @NotNull
    @JsonProperty("ReferenceCode")
    private String referenceCode;

    @NotNull
    @JsonProperty("Url")
    private String url;

    @JacksonXmlProperty(isAttribute = true)
    private String timestamp;

    @Override
    public boolean isPopulated() {
        return getUrl() != null && getReferenceCode() != null;
    }
}
