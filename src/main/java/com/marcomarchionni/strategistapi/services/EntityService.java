package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.dtos.request.EntitySave;

public interface EntityService<T extends EntitySave, R> {

    R create(T dto);

    R update(T dto);

    void deleteById(Long id);

    ServiceType getServiceType();

    enum ServiceType {
        PORTFOLIO,
        STRATEGY,
        TRADE,
        POSITION,
        DIVIDEND
    }// Return the type of service
}

