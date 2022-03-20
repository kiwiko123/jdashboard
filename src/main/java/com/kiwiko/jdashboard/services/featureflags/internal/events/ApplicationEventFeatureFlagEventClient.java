package com.kiwiko.jdashboard.services.featureflags.internal.events;

import com.kiwiko.jdashboard.webapp.application.events.api.dto.ApplicationEvent;
import com.kiwiko.jdashboard.webapp.application.events.queue.api.dto.ApplicationEventQueueItem;
import com.kiwiko.jdashboard.webapp.application.events.queue.api.interfaces.ApplicationEventQueue;
import com.kiwiko.jdashboard.services.featureflags.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.services.featureflags.api.interfaces.FeatureFlagEventClient;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;

import javax.inject.Inject;
import java.util.Objects;

public class ApplicationEventFeatureFlagEventClient implements FeatureFlagEventClient {

    @Inject private ApplicationEventQueue applicationEventQueue;
    @Inject private GsonProvider gsonProvider;

    @Override
    public void createCreateFeatureFlagEvent(FeatureFlag featureFlag) {
        ApplicationEvent applicationEvent = ApplicationEvent.newBuilder(FeatureFlagEventConstants.CREATE_FEATURE_FLAG_EVENT_TYPE)
                .setMetadata(gsonProvider.getDefault().toJson(featureFlag))
                .build();

        ApplicationEventQueueItem queueItem = new ApplicationEventQueueItem();
        queueItem.setApplicationEvent(applicationEvent);

        applicationEventQueue.enqueue(queueItem);
    }

    @Override
    public void createUpdateFeatureFlagEvent(FeatureFlag featureFlag) {
        ApplicationEventQueueItem queueItem = createFeatureFlagUpdateEventItem(featureFlag, FeatureFlagEventConstants.UPDATE_FEATURE_FLAG_EVENT_TYPE);
        applicationEventQueue.enqueue(queueItem);
    }

    @Override
    public void createMergeFeatureFlagEvent(FeatureFlag featureFlag) {
        ApplicationEventQueueItem queueItem = createFeatureFlagUpdateEventItem(featureFlag, FeatureFlagEventConstants.MERGE_FEATURE_FLAG_EVENT_TYPE);
        applicationEventQueue.enqueue(queueItem);
    }

    @Override
    public void createDeleteFeatureFlagEvent(long featureFlagId) {
        ApplicationEvent applicationEvent = ApplicationEvent.newBuilder(FeatureFlagEventConstants.DELETE_FEATURE_FLAG_EVENT_TYPE)
                .setEventKey(Long.toString(featureFlagId))
                .build();

        ApplicationEventQueueItem queueItem = new ApplicationEventQueueItem();
        queueItem.setApplicationEvent(applicationEvent);

        applicationEventQueue.enqueue(queueItem);
    }

    private ApplicationEventQueueItem createFeatureFlagUpdateEventItem(FeatureFlag featureFlag, String eventType) {
        Objects.requireNonNull(featureFlag.getId(), "ID is required to update a feature flag");

        ApplicationEvent applicationEvent = ApplicationEvent.newBuilder(eventType)
                .setEventKey(featureFlag.getId().toString())
                .setMetadata(gsonProvider.getDefault().toJson(featureFlag))
                .build();

        ApplicationEventQueueItem queueItem = new ApplicationEventQueueItem();
        queueItem.setApplicationEvent(applicationEvent);

        return queueItem;
    }
}
