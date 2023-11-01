package com.kiwiko.jdashboard.featureflags.service.internal;

import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.TableRecordVersionClient;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.GetLastUpdatedInput;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.GetLastUpdatedOutput;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.VersionRecord;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserAssociation;
import com.kiwiko.jdashboard.tablerecordversions.service.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FeatureFlagStatusResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureFlagStatusResolver.class);

    @Inject private TableRecordVersionClient tableRecordVersionClient;

    public String getStatus(FeatureFlag featureFlag, @Nullable FeatureFlagUserAssociation userAssociation) {
        return getStatusWithoutFallback(featureFlag, userAssociation)
                .orElse(featureFlag.getStatus());
    }

    /**
     * Given a feature flag and a user association, determine which {@link com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagStatus}
     * to use in order for this flag to be considered "resolved".
     *
     * If any unexpected scenario occurs in which the flag cannot be resolved with absolute certainty, then an empty
     * optional will be returned.
     *
     * The flag status associated with the most recently modified predicate will be chosen.
     */
    private Optional<String> getStatusWithoutFallback(FeatureFlag featureFlag, @Nullable FeatureFlagUserAssociation userAssociation) {
        if (userAssociation == null) {
            return Optional.empty();
        }

        String featureFlagTableName = "feature_flags";
        String userAssociationTableName = "feature_flag_user_associations";

        Map<String, String> flagStatusByTableName = new HashMap<>();
        if (!featureFlag.getIsRemoved()) {
            flagStatusByTableName.put(featureFlagTableName, featureFlag.getStatus());
        }
        if (!userAssociation.getIsRemoved()) {
            flagStatusByTableName.put(userAssociationTableName, userAssociation.getFeatureFlagStatus());
        }

        if (flagStatusByTableName.size() <= 1) {
            return flagStatusByTableName.values().stream().findFirst();
        }

        VersionRecord featureFlagVersionRecord = new VersionRecord(featureFlagTableName, featureFlag.getId());
        VersionRecord userAssociationVersionRecord = new VersionRecord(userAssociationTableName, userAssociation.getId());
        Set<VersionRecord> versionRecords = Set.of(featureFlagVersionRecord, userAssociationVersionRecord);

        GetLastUpdatedInput getFeatureFlagLastUpdatedInput = new GetLastUpdatedInput();
        getFeatureFlagLastUpdatedInput.setVersionRecords(versionRecords);

        ClientResponse<GetLastUpdatedOutput> getLastUpdatedOutput = tableRecordVersionClient.getLastUpdated(getFeatureFlagLastUpdatedInput);
        if (!getLastUpdatedOutput.isSuccessful()) {
            LOGGER.warn(
                    "Unable to resolve flag status for feature flag {} ({}). An error occurred retrieving the last updated information: {}",
                    featureFlag.getId(),
                    featureFlag.getName(),
                    getLastUpdatedOutput.getError());
            return Optional.empty();
        }

        Map<String, TableRecordVersion> versionsByTableName = getLastUpdatedOutput.getPayload().getLastUpdatedVersions().stream()
                .collect(Collectors.toMap(TableRecordVersion::getTableName, Function.identity()));

        return versionsByTableName.entrySet().stream()
                .filter(entry -> flagStatusByTableName.get(entry.getKey()) != null)
                .max(Comparator.comparing(entry -> entry.getValue().getCreatedDate()))
                .map(Map.Entry::getKey)
                .map(flagStatusByTableName::get);
    }
}
