package com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.internal.data;

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
@Table(name = "incoming_application_request_logs")
public class IncomingApplicationRequestLogEntity implements DataEntity {
    private Long id;
    private String uri;
    private @Nullable String remoteHost;
    private @Nullable String ipAddress;
    private Instant requestDate;
    private @Nullable Long userId;

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

    @Column(name = "uri", nullable = false)
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Nullable
    @Column(name = "remote_host")
    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(@Nullable String remoteHost) {
        this.remoteHost = remoteHost;
    }

    @Nullable
    @Column(name = "ip_address")
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(@Nullable String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Column(name = "request_date", nullable = false)
    public Instant getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    @Nullable
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }
}
