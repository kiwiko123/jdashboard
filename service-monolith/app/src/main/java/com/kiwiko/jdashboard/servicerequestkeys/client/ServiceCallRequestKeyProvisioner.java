package com.kiwiko.jdashboard.servicerequestkeys.client;

public interface ServiceCallRequestKeyProvisioner {
    
    ProvisionServiceRequestKeyOutput provisionInternalServiceRequestKey(ProvisionServiceRequestKeyInput input) throws ProvisionServiceRequestKeyException;

    ProvisionServiceRequestKeyOutput provisionExternalServiceRequestKey(ProvisionServiceRequestKeyInput input) throws ProvisionServiceRequestKeyException;
}
