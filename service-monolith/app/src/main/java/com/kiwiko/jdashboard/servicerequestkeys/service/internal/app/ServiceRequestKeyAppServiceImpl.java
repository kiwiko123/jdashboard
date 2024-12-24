package com.kiwiko.jdashboard.servicerequestkeys.service.internal.app;

import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.ServiceRequestKey;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.ServiceRequestKeyService;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.app.GetServiceRequestKeysResponse;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.app.ServiceRequestKeyAppService;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class ServiceRequestKeyAppServiceImpl implements ServiceRequestKeyAppService {
    @Inject private ServiceRequestKeyService serviceRequestKeyService;

    @Override
    public GetServiceRequestKeysResponse getServiceRequestKeysForUser(long userId) {
        List<ServiceRequestKey> serviceRequestKeys = serviceRequestKeyService.getForUsers(Collections.singleton(userId));

        GetServiceRequestKeysResponse response = new GetServiceRequestKeysResponse();
        response.setServiceRequestKeys(serviceRequestKeys);
        return response;
    }

    @Override
    public ServiceRequestKey create(ServiceRequestKey serviceRequestKey) {
        return serviceRequestKeyService.create(serviceRequestKey);
    }
}
