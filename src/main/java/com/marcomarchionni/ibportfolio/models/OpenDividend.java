package com.marcomarchionni.ibportfolio.models;

import lombok.Builder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("OPEN")
public class OpenDividend extends Dividend {

    public OpenDividend() {
        super();
    }
}
