package com.marcomarchionni.ibportfolio.model.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("OPEN")
public class OpenDividend extends Dividend {

    public OpenDividend() {
        super();
    }
}
