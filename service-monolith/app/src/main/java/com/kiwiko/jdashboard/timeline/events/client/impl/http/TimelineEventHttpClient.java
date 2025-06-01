package com.kiwiko.jdashboard.timeline.events.client.impl.http;

import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.timeline.events.client.api.CreateTimelineEventInput;
import com.kiwiko.jdashboard.timeline.events.client.api.TimelineEventClient;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class TimelineEventHttpClient implements TimelineEventClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimelineEventHttpClient.class);
    @Inject private JdashboardApiClient jdashboardApiClient;

    @Override
    public void pushNewTimelineEvent(CreateTimelineEventInput input) {
        CreateTimelineEventRequest request = new CreateTimelineEventRequest(input);

        try {
            jdashboardApiClient.asynchronousCall(request)
                    .exceptionally((e) -> {
                        LOGGER.error("An unexpected error occurred attempting to push a new timeline event", e);
                        return null;
                    });
        } catch (ClientException e) {
            LOGGER.error(
                    "A client-side error occurred attempting to push a new timeline event. Control flow did not stop because this is a non-blocking method.",
                    e);
        }
    }
}
