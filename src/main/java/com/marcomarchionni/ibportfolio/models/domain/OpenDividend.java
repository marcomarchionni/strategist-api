package com.marcomarchionni.ibportfolio.models.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("OPEN")
public class OpenDividend extends Dividend {

    public OpenDividend() {
        super();
    }
}
