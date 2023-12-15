package com.marcomarchionni.ibportfolio.dtos.flex;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@JacksonXmlRootElement(localName = "FlexStatementResponse")
public class FlexStatementResponseDto {

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
}
