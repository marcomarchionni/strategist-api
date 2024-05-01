package com.marcomarchionni.strategistapi.dtos.request;

import com.marcomarchionni.strategistapi.services.EntityService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatchOperation<T extends EntitySave> {
    private String method;
    private Long entityId;
    private T dto;
    private EntityService.ServiceType serviceType;

}
