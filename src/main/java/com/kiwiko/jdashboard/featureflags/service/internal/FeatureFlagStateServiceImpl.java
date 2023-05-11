package com.kiwiko.jdashboard.featureflags.service.internal;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagState;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagStatus;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserAssociation;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagDoesNotExistException;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagService;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagStateService;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

public class FeatureFlagStateServiceImpl implements FeatureFlagStateService {
    @Inject private FeatureFlagService featureFlagService;
    @Inject private FeatureFlagUserAssociationEntityService featureFlagUserAssociationService;
    @Inject private FeatureFlagStateMapper featureFlagStateMapper;
    @Inject private FeatureFlagStatusResolver featureFlagStatusResolver;

    private FeatureFlagState createForPublic(FeatureFlagState featureFlagState) {
        verifyFlagDoesNotExistForCreation(featureFlagState);
        FeatureFlag createdFeatureFlag = createFlag(featureFlagState, FeatureFlagUserScope.PUBLIC.getId());
        return featureFlagStateMapper.mapToFeatureFlagState(createdFeatureFlag);
    }

    private FeatureFlagState createForUser(FeatureFlagState featureFlagState, Long userId) {
        verifyFlagDoesNotExistForCreation(featureFlagState);
        FeatureFlag createdFeatureFlag = createFlag(featureFlagState, FeatureFlagUserScope.INDIVIDUAL.getId());

        FeatureFlagUserAssociation userAssociationToCreate = new FeatureFlagUserAssociation();
        userAssociationToCreate.setFeatureFlagId(createdFeatureFlag.getId());
        userAssociationToCreate.setUserId(userId);
        userAssociationToCreate.setFeatureFlagStatus(featureFlagState.getStatus());
        featureFlagUserAssociationService.create(userAssociationToCreate);

        return featureFlagStateMapper.mapToFeatureFlagState(createdFeatureFlag);
    }

    private void verifyFlagDoesNotExistForCreation(FeatureFlagState featureFlagState) {
        Optional<FeatureFlag> existingFeatureFlag = featureFlagService.getByName(featureFlagState.getName());
        if (existingFeatureFlag.isPresent()) {
            throw new EntityExistsException(String.format("A feature flag named \"%s\" already exists", featureFlagState.getName()));
        }
    }

    private FeatureFlag createFlag(FeatureFlagState featureFlagState, String userScope) {
        FeatureFlag featureFlagToCreate = new FeatureFlag();
        featureFlagToCreate.setName(featureFlagState.getName());
        featureFlagToCreate.setValue(featureFlagState.getValue());
        featureFlagToCreate.setStatus(featureFlagState.getStatus());
        featureFlagToCreate.setUserScope(userScope);
        featureFlagToCreate.setIsRemoved(false);

        return featureFlagService.create(featureFlagToCreate);
    }

    private FeatureFlag updateFlag(FeatureFlagState featureFlagState, String userScope) {
        FeatureFlag existingFeatureFlag = featureFlagService.getByName(featureFlagState.getName())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No feature flag named \"%s\" was found", featureFlagState.getName())));

        existingFeatureFlag.setName(featureFlagState.getName());
        existingFeatureFlag.setValue(featureFlagState.getValue());
        existingFeatureFlag.setStatus(featureFlagState.getStatus());
        existingFeatureFlag.setUserScope(userScope);

        return featureFlagService.update(existingFeatureFlag);
    }

    private FeatureFlagState updateForPublic(FeatureFlagState featureFlagState) {
        FeatureFlag updatedFeatureFlag = updateFlag(featureFlagState, FeatureFlagUserScope.PUBLIC.getId());
        return featureFlagStateMapper.mapToFeatureFlagState(updatedFeatureFlag);
    }

    private FeatureFlagState updateForUser(FeatureFlagState featureFlagState, Long userId) {
        FeatureFlag existingFeatureFlag = featureFlagService.getByName(featureFlagState.getName())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No feature flag named \"%s\" was found", featureFlagState.getName())));

        FeatureFlag updatedFeatureFlag = updateFlag(featureFlagState, FeatureFlagUserScope.INDIVIDUAL.getId());
        Long featureFlagId = updatedFeatureFlag.getId();

        // get user association by flag ID + user ID
        FeatureFlagUserAssociation userAssociation = featureFlagUserAssociationService.getByFeatureFlagIdForUser(featureFlagId, userId).orElse(null);
        if (userAssociation == null) {
            FeatureFlagUserAssociation userAssociationToCreate = new FeatureFlagUserAssociation();
            userAssociationToCreate.setFeatureFlagId(featureFlagId);
            userAssociationToCreate.setUserId(userId);
            userAssociationToCreate.setFeatureFlagStatus(featureFlagState.getStatus());
            userAssociation = featureFlagUserAssociationService.create(userAssociationToCreate);
        } else {
            userAssociation.setFeatureFlagStatus(featureFlagState.getStatus());
        }

        userAssociation.setFeatureFlagStatus(updatedFeatureFlag.getStatus());

        boolean isDisablingFlagForUser = FeatureFlagStatus.ENABLED.getId().equals(existingFeatureFlag.getStatus()) && FeatureFlagStatus.DISABLED.getId().equals(updatedFeatureFlag.getStatus());
        userAssociation.setIsRemoved(isDisablingFlagForUser);

        featureFlagUserAssociationService.update(userAssociation);

        return featureFlagStateMapper.mapToFeatureFlagState(updatedFeatureFlag);
    }

    @Override
    public Optional<FeatureFlagState> getPublicFlagByName(String name) {
        return getUserFlagByName(name, null);
    }

    @Override
    public Optional<FeatureFlagState> getUserFlagByName(String name, Long userId) {
        FeatureFlag featureFlag = featureFlagService.getByName(name).orElse(null);
        if (featureFlag == null) {
            return Optional.empty();
        }

        FeatureFlagUserAssociation userAssociation = Optional.ofNullable(userId)
                .flatMap(id -> featureFlagUserAssociationService.getByFeatureFlagIdForUser(featureFlag.getId(), id))
                .orElse(null);
        String flagStatus = featureFlagStatusResolver.getStatus(featureFlag, userAssociation);
        FeatureFlagState state = featureFlagStateMapper.mapToFeatureFlagState(featureFlag);
        state.setStatus(flagStatus);

        return Optional.of(state);
    }

    @Override
    public FeatureFlagState toggle(String name) {
        FeatureFlagState existingState = getPublicFlagByName(name)
                .orElseThrow(() -> new FeatureFlagDoesNotExistException(String.format("Cannot toggle feature flag %s because it does not exist", name)));

        return setForPublic(name, FeatureFlagStatus.opposite(existingState.getStatus()));
    }

    @Override
    public FeatureFlagState toggle(String name, Long userId) {
        FeatureFlagState existingState = getUserFlagByName(name, userId)
                .orElseThrow(() -> new FeatureFlagDoesNotExistException(String.format("Cannot toggle feature flag %s because it does not exist", name)));

        return setForUser(name, userId, FeatureFlagStatus.opposite(existingState.getStatus()));
    }

    @Override
    public FeatureFlagState turnOn(String name) {
        return setForPublic(name, FeatureFlagStatus.ENABLED.getId());
    }

    @Override
    public FeatureFlagState turnOn(String name, Long userId) {
        Objects.requireNonNull(userId, "A user ID is required to turn on a feature flag");
        return setForUser(name, userId, FeatureFlagStatus.ENABLED.getId());
    }

    @Override
    public FeatureFlagState turnOff(String name) {
        return setForPublic(name, FeatureFlagStatus.DISABLED.getId());
    }

    @Override
    public FeatureFlagState turnOff(String name, Long userId) {
        Objects.requireNonNull(userId, "A user ID is required to turn off a feature flag");
        return setForUser(name, userId, FeatureFlagStatus.DISABLED.getId());
    }

    private FeatureFlagState setForPublic(String featureFlagName, String newFeatureFlagStatus) {
        FeatureFlagState existingState = getPublicFlagByName(featureFlagName).orElse(null);

        if (existingState == null) {
            FeatureFlagState stateToCreate = new FeatureFlagState();
            stateToCreate.setName(featureFlagName);
            stateToCreate.setStatus(newFeatureFlagStatus);
            return createForPublic(stateToCreate);
        }

        existingState.setStatus(newFeatureFlagStatus);
        return updateForPublic(existingState);
    }

    private FeatureFlagState setForUser(String featureFlagName, Long userId, String newFeatureFlagStatus) {
        FeatureFlagState existingState = getUserFlagByName(featureFlagName, userId).orElse(null);

        if (existingState == null) {
            FeatureFlagState stateToCreate = new FeatureFlagState();
            stateToCreate.setName(featureFlagName);
            stateToCreate.setStatus(newFeatureFlagStatus);
            return createForUser(stateToCreate, userId);
        }

        existingState.setStatus(newFeatureFlagStatus);
        return updateForUser(existingState, userId);
    }
}
