package com.marcomarchionni.strategistapi.services.resolvers;

import com.marcomarchionni.strategistapi.dtos.request.EntitySave;
import com.marcomarchionni.strategistapi.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceResolverImpl implements ServiceResolver {

    private final Map<EntityService.ServiceType, EntityService<?, ?>> serviceMap = new HashMap<>();

    @Autowired
    public ServiceResolverImpl(List<EntityService<?, ?>> services) {
        for (EntityService<?, ?> service : services) {
            serviceMap.put(service.getServiceType(), service);
        }
    }

    @Override
    public <T extends EntitySave, R> EntityService<T, R> resolveService(EntityService.ServiceType serviceType) {
        EntityService<?, ?> rawService = serviceMap.get(serviceType);
        if (rawService == null) {
            throw new IllegalArgumentException("No service registered for type: " + serviceType);
        }
        return (EntityService<T, R>) rawService;
    }
}
