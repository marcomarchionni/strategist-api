package com.marcomarchionni.ibportfolio.model.dtos.flex;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FlexStatementResponse")
public class FlexStatementResponseDto {

    @XmlElement(name = "Status")
    private String status;

    @XmlElement(name = "ReferenceCode")
    private String referenceCode;

    @XmlElement(name = "Url")
    private String url;

    public String getUrl() {
        return url;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    @XmlAttribute(name = "timestamp")
    private String timestamp;
}
