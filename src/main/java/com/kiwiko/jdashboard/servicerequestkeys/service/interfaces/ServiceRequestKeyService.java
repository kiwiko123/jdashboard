package com.kiwiko.jdashboard.servicerequestkeys.service.interfaces;

import java.util.Optional;

public interface ServiceRequestKeyService {

    Optional<ServiceRequestKey> get(long id);

    Optional<ServiceRequestKey> getByToken(String token);

    ServiceRequestKey create(ServiceRequestKey serviceRequestKey);

    ServiceRequestKey update(ServiceRequestKey serviceRequestKey);

    ServiceRequestKey merge(ServiceRequestKey serviceRequestKey);
}
