package com.kiwiko.jdashboard.servicerequestkeys.service.interfaces;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ServiceRequestKeyService {

    Optional<ServiceRequestKey> get(long id);

    Optional<ServiceRequestKey> getByToken(String token);

    List<ServiceRequestKey> getForUsers(Collection<Long> userIds);

    ServiceRequestKey create(ServiceRequestKey serviceRequestKey);

    ServiceRequestKey update(ServiceRequestKey serviceRequestKey);

    ServiceRequestKey merge(ServiceRequestKey serviceRequestKey);
}
