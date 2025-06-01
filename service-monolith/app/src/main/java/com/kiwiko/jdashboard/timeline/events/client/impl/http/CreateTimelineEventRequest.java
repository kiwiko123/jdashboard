package com.kiwiko.jdashboard.timeline.events.client.impl.http;

import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.timeline.events.client.api.CreateTimelineEventInput;
import com.kiwiko.jdashboard.timeline.events.client.api.CreateTimelineEventOutput;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

class CreateTimelineEventRequest extends JdashboardApiRequest {
    private final CreateTimelineEventInput createTimelineEventInput;
    private final RequestUrl requestUrl;

    public CreateTimelineEventRequest(CreateTimelineEventInput createTimelineEventInput) {
        this.createTimelineEventInput = createTimelineEventInput;
        requestUrl = RequestUrl.fromPartial(new UriBuilder().setPath("/timeline-api/service-client/timeline-events"));
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return requestUrl;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return CreateTimelineEventOutput.class;
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return createTimelineEventInput;
    }

    @Nullable
    @Override
    public Duration getRequestTimeout() {
        return Duration.ofSeconds(5);
    }

    @Override
    public Set<RequestHeader> getRequestHeaders() {
        return Collections.singleton(RequestHeaders.CONTENT_TYPE_JSON);
    }

    @Nullable
    @Override
    public String getClientIdentifier() {
        return "timeline-event-service-client";
    }
}
