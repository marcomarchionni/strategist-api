package com.marcomarchionni.ibportfolio.model.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLOSED")
public class ClosedDividend extends Dividend {

    public ClosedDividend() {
        super();
    }
}
