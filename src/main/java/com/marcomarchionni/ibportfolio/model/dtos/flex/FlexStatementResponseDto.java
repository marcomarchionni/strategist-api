package com.marcomarchionni.ibportfolio.model.dtos.flex;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "FlexStatementResponse")
public class FlexStatementResponseDto {

    @XmlElement(name = "Status")
    private String status;

    @XmlElement(name = "ReferenceCode")
    private String referenceCode;

    @XmlElement(name = "Url")
    private String url;

    @XmlAttribute(name = "timestamp")
    private String timestamp;
}
