package com.marcomarchionni.strategistapi.services.resolvers;

import com.marcomarchionni.strategistapi.dtos.request.EntitySave;
import com.marcomarchionni.strategistapi.services.EntityService;

public interface ServiceResolver {
    <T extends EntitySave, R> EntityService<T, R> resolveService(EntityService.ServiceType serviceType);
}
