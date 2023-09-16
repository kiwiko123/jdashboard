package com.kiwiko.jdashboard.featureflags.service.internal.data;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.LongDataEntity;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "feature_flag_contexts")
public class FeatureFlagContextEntity implements LongDataEntity {
    private Long id;
    private Long featureFlagId;
    private String scope;
    private @Nullable Long userId;
    private String flagStatus;
    private @Nullable String flagValue;
    private Instant startDate;
    private @Nullable Instant endDate;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "feature_flag_id", nullable = false)
    public Long getFeatureFlagId() {
        return featureFlagId;
    }

    public void setFeatureFlagId(Long featureFlagId) {
        this.featureFlagId = featureFlagId;
    }

    @Column(name = "scope", nullable = false)
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Column(name = "user_id")
    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    @Column(name = "flag_status", nullable = false)
    public String getFlagStatus() {
        return flagStatus;
    }

    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
    }

    @Column(name = "flag_value")
    @Nullable
    public String getFlagValue() {
        return flagValue;
    }

    public void setFlagValue(@Nullable String flagValue) {
        this.flagValue = flagValue;
    }

    @Column(name = "start_date", nullable = false)
    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    @Nullable
    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(@Nullable Instant endDate) {
        this.endDate = endDate;
    }
}
