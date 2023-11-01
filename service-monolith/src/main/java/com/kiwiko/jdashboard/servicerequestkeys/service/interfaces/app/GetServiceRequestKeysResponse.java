package com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.app;

import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.ServiceRequestKey;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetServiceRequestKeysResponse {
    private List<ServiceRequestKey> serviceRequestKeys;
}
