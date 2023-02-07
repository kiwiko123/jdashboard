package com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ProvisionServiceRequestKeyInput;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ProvisionServiceRequestKeyException;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ProvisionServiceRequestKeyOutput;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ServiceCallRequestKeyProvisioner;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.application.events.api.dto.ApplicationEvent;
import com.kiwiko.jdashboard.webapp.application.events.api.interfaces.ApplicationEventService;
import com.kiwiko.jdashboard.webapp.framework.json.gson.adapters.InstantTextAdapter;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.dto.UniversalUniqueIdentifier;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.UniqueIdentifierService;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.parameters.CreateUuidParameters;

import javax.inject.Inject;
import java.time.Instant;
import java.time.format.DateTimeParseException;

public class JdashboardServiceCallRequestKeyProvisioner implements ServiceCallRequestKeyProvisioner {
    private static final Gson SERIALIZER = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new InstantTextAdapter())
            .create();
    
    @Inject private ApplicationEventService applicationEventService;
    @Inject private UniqueIdentifierService uniqueIdentifierService;
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
        String eventKey = String.format("%s-%d", input.getServiceClientIdentifier(), Instant.now().toEpochMilli());
        Instant expirationTime = getExpirationTime(input);
        RequestKeyEventMetadata requestEventMetadata = RequestKeyEventMetadata.builder()
                .scope(scope)
                .serviceClientId(input.getServiceClientIdentifier())
                .expirationTime(expirationTime)
                .description(input.getDescription())
                .build();
        ApplicationEvent requestEvent = ApplicationEvent.newBuilder(ServiceCallRequestKeyConstants.PROVISION_REQUEST_KEY_APPLICATION_EVENT_NAME)
                .setEventKey(eventKey)
                .setMetadata(SERIALIZER.toJson(requestEventMetadata))
                .build();
        ApplicationEvent createdEvent = applicationEventService.create(requestEvent);

        String referenceKey = String.format("applicationEventId:%d", createdEvent.getId());
        CreateUuidParameters createUuidParameters = new CreateUuidParameters(referenceKey);
        UniversalUniqueIdentifier uniqueIdentifier = uniqueIdentifierService.create(createUuidParameters);

        return ProvisionServiceRequestKeyOutput.builder()
                .requestKeyHeaderName(ServiceCallRequestKeyConstants.SERVICE_CLIENT_IDENTIFIER_REQUEST_HEADER_NAME)
                .requestKey(uniqueIdentifier.getUuid())
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
