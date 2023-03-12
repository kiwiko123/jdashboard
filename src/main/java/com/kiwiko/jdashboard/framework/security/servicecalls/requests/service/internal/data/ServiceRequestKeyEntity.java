package com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.data;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "service_request_keys")
public class ServiceRequestKeyEntity implements DataEntity {
    private Long id;
    private String scope;
    private String serviceClientName;
    private @Nullable String description;
    private Instant createdDate;
    private @Nullable Long createdByUserId;
    private Instant expirationDate;
    private String requestToken;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "scope", nullable = false)
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Column(name = "service_client_name", nullable = false)
    public String getServiceClientName() {
        return serviceClientName;
    }

    public void setServiceClientName(String serviceClientName) {
        this.serviceClientName = serviceClientName;
    }

    @Column(name = "description")
    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Column(name = "created_date", nullable = false)
    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "created_by_user_id")
    @Nullable
    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(@Nullable Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    @Column(name = "expiration_date", nullable = false)
    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Column(name = "request_token", nullable = false)
    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }
}
