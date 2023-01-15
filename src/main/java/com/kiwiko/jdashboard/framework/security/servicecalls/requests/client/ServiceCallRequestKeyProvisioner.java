package com.kiwiko.jdashboard.framework.security.servicecalls.requests.client;

public interface ServiceCallRequestKeyProvisioner {
    
    ProvisionServiceRequestKeyOutput provisionInternalServiceRequestKey(ProvisionServiceRequestKeyInput input) throws ProvisionServiceRequestKeyException;

    ProvisionServiceRequestKeyOutput provisionExternalServiceRequestKey(ProvisionServiceRequestKeyInput input) throws ProvisionServiceRequestKeyException;
}
