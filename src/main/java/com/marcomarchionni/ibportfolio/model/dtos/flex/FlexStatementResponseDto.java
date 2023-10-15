package com.marcomarchionni.ibportfolio.model.dtos.flex;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;


@Data
@JacksonXmlRootElement(localName = "FlexStatementResponse")
public class FlexStatementResponseDto {

    @JsonProperty("Status")
    private String status;

    @JsonProperty("ReferenceCode")
    private String referenceCode;

    @JsonProperty("Url")
    private String url;

    @JacksonXmlProperty(isAttribute = true)
    private String timestamp;
}
