package com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.app;

import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.ServiceRequestKey;

public interface ServiceRequestKeyAppService {
    GetServiceRequestKeysResponse getServiceRequestKeysForUser(long userId);

    ServiceRequestKey create(ServiceRequestKey serviceRequestKey);
}
