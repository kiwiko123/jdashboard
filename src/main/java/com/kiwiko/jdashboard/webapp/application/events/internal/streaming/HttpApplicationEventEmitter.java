package com.kiwiko.jdashboard.webapp.application.events.internal.streaming;

import com.kiwiko.jdashboard.library.http.client.api.exceptions.ClientException;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ResponseStatus;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardApiClient;

import javax.inject.Inject;

public class HttpApplicationEventEmitter implements ApplicationEventEmitter {

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private Logger logger;

    @Override
    public void emit(EmitApplicationEventRequest request) {
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
}
