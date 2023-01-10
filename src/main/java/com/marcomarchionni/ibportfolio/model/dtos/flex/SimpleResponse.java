package com.marcomarchionni.ibportfolio.model.dtos.flex;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@XmlRootElement(name = "SimpleResponse")
public class SimpleResponse {
    public FlexStatements FlexStatements;
    public String queryName;
    public String type;

    @Getter
    public static class FlexStatements {
        public FlexStatement FlexStatement;
        public int count;
    }

    @Getter
    public static class FlexStatement {
        public String accountId;
        @JsonFormat(pattern = "yyyyMMdd")
        public LocalDate fromDate;
        @JsonFormat(pattern = "yyyyMMdd")
        public LocalDate toDate;
        public String period;
        @JsonFormat(pattern = "yyyyMMdd;HHmmss")
        public LocalDateTime whenGenerated;
    }
}

