package com.marcomarchionni.ibportfolio.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("OPEN")
public class OpenDividend extends Dividend {

    public OpenDividend() {
        super();
    }
}
