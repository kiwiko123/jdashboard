package com.kiwiko.jdashboard.webapp.application.events.internal.streaming;

import com.kiwiko.jdashboard.clients.featureflags.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.clients.featureflags.api.dto.FeatureFlagStatus;
import com.kiwiko.jdashboard.clients.featureflags.api.interfaces.FeatureFlagClient;
import com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters.GetFeatureFlagInput;
import com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters.GetFeatureFlagOutput;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.ResponseStatus;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpApplicationEventEmitter implements ApplicationEventEmitter {
    private static final Set<String> RESERVED_PREFIXES = Set.of("__JDASHBOARD_INTERNAL");
    private static final String ENABLED_EVENT_TYPES_FLAG_NAME = "JDASHBOARD_INTERNAL_APPLICATION_EVENT_EMITTER_HTTP_ENABLED_EVENT_TYPES";

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private FeatureFlagClient featureFlagClient;
    @Inject private Logger logger;

    @Override
    public void emit(EmitApplicationEventRequest request) {
        if (!shouldEmitEvent(request)) {
            return;
        }

        String url = getEventEndpoint(request.getEvent().getEventType());
        HttpEmitApplicationEventRequest apiRequest = new HttpEmitApplicationEventRequest(request, url);

        try {
            jdashboardApiClient.asynchronousCall(apiRequest)
                    .exceptionally(exception -> handleRequestException(exception, request));
        } catch (ClientException e) {
            logger.error("Error attempting to emit application event {}", request, e);
        }
    }

    private String getEventEndpoint(String eventType) {
        return String.format("/application-event-streaming/api/types/%s/event", eventType);
    }

    private ClientResponse<Object> handleRequestException(Throwable throwable, EmitApplicationEventRequest request) {
        logger.error("Error emitting application event request {}", request, throwable);
        return new ClientResponse<>(null, ResponseStatus.fromMessage(throwable.getMessage()));
    }

    private boolean shouldEmitEvent(EmitApplicationEventRequest request) {
        boolean startsWithReservedPrefix = RESERVED_PREFIXES.stream()
                .anyMatch(request.getEvent().getEventType().toUpperCase()::startsWith);
        if (startsWithReservedPrefix) {
            return false;
        }

        GetFeatureFlagInput getFeatureFlagInput = GetFeatureFlagInput.builder()
                .featureFlagName(ENABLED_EVENT_TYPES_FLAG_NAME)
                .build();
        GetFeatureFlagOutput getFeatureFlagOutput = featureFlagClient.getFlag(getFeatureFlagInput);
        FeatureFlag featureFlag = getFeatureFlagOutput.getFeatureFlag();

        if (featureFlag == null) {
            logger.warn("No feature flag name {}", ENABLED_EVENT_TYPES_FLAG_NAME);
            return false;
        }

        if (featureFlag.getValue() == null) {
            logger.warn("No value for flag {}", ENABLED_EVENT_TYPES_FLAG_NAME);
            return false;
        }

        if (Objects.equals(featureFlag.getStatus(), FeatureFlagStatus.DISABLED.getId())) {
            logger.debug("Feature flag {} is disabled", ENABLED_EVENT_TYPES_FLAG_NAME);
            return false;
        }

        String commaDelimitedEnabledTypes = featureFlag.getValue();
        Set<String> enabledEventTypes = Arrays.stream(commaDelimitedEnabledTypes.split(","))
                .map(String::toUpperCase)
                .collect(Collectors.toUnmodifiableSet());

        boolean eventTypeEnabled = enabledEventTypes.contains(request.getEvent().getEventType().toUpperCase());
        if (!eventTypeEnabled) {
            logger.debug("Application event type emitter disabled for type {}", request.getEvent().getEventType());
        }
        return eventTypeEnabled;
    }
}
