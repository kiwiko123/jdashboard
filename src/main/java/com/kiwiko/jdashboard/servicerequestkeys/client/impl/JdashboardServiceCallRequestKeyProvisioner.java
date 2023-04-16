package com.kiwiko.jdashboard.servicerequestkeys.client.impl;

import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyInput;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyException;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyOutput;
import com.kiwiko.jdashboard.servicerequestkeys.client.ServiceCallRequestKeyProvisioner;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.ServiceRequestKey;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.ServiceRequestKeyService;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;

import javax.inject.Inject;
import java.time.Instant;
import java.time.format.DateTimeParseException;

public class JdashboardServiceCallRequestKeyProvisioner implements ServiceCallRequestKeyProvisioner {

    @Inject private ServiceRequestKeyService serviceRequestKeyService;
    @Inject private Logger logger;

    @Override
    public ProvisionServiceRequestKeyOutput provisionInternalServiceRequestKey(ProvisionServiceRequestKeyInput input) throws ProvisionServiceRequestKeyException {
        return provisionServiceRequestKey(input, "internal");
    }

    @Override
    public ProvisionServiceRequestKeyOutput provisionExternalServiceRequestKey(ProvisionServiceRequestKeyInput input) throws ProvisionServiceRequestKeyException {
        return provisionServiceRequestKey(input, "external");
    }
    
    private ProvisionServiceRequestKeyOutput provisionServiceRequestKey(ProvisionServiceRequestKeyInput input, String scope) throws ProvisionServiceRequestKeyException {
        Instant expirationTime = getExpirationTime(input);

        ServiceRequestKey serviceRequestKey = new ServiceRequestKey();
        serviceRequestKey.setScope(scope);
        serviceRequestKey.setServiceClientName(input.getServiceClientIdentifier());
        serviceRequestKey.setExpirationDate(expirationTime);
        serviceRequestKey.setDescription(input.getDescription());

        ServiceRequestKey createdServiceRequestKey = serviceRequestKeyService.create(serviceRequestKey);

        return ProvisionServiceRequestKeyOutput.builder()
                .requestKeyHeaderName(ServiceCallRequestKeyConstants.SERVICE_CLIENT_IDENTIFIER_REQUEST_HEADER_NAME)
                .requestKey(createdServiceRequestKey.getRequestToken())
                .expirationTime(expirationTime.toString())
                .build();
    }

    private <T extends ProvisionServiceRequestKeyInput> Instant getExpirationTime(T input) {
        Instant now = Instant.now();
        Instant defaultExpirationTime = now.plus(ServiceCallRequestKeyConstants.DEFAULT_REQUEST_KEY_EXPIRATION_DURATION);
        if (input.getExpirationTime() == null) {
            return defaultExpirationTime;
        }
        
        Instant requestedExpirationTime;
        try {
            requestedExpirationTime = Instant.parse(input.getExpirationTime());
        } catch (DateTimeParseException e) {
            logger.info("Error parsing requested expiration date \"{}\" for service client ID {}. Using default expiration time.", input.getExpirationTime(), input.getServiceClientIdentifier(), e);
            return defaultExpirationTime;
        }

        if (requestedExpirationTime.isAfter(now.plus(ServiceCallRequestKeyConstants.MAX_CUSTOM_REQUEST_KEY_EXPIRATION_DURATION))) {
            logger.info("Requested expiration time for developer service client ID {} exceeds the maximum duration. Using default expiration time.", input.getServiceClientIdentifier());
            return defaultExpirationTime;
        }

        return requestedExpirationTime;
    }
}
