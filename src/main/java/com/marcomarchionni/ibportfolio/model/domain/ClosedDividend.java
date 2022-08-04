package com.marcomarchionni.ibportfolio.model.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLOSED")
public class ClosedDividend extends Dividend {

    public ClosedDividend() {
        super();
    }
}
