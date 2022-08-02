package com.marcomarchionni.ibportfolio.models.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLOSED")
public class ClosedDividend extends Dividend {

    public ClosedDividend() {
        super();
    }
}
