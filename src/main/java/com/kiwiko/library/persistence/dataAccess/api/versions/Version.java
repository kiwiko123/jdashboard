package com.kiwiko.library.persistence.dataAccess.api.versions;

import com.kiwiko.library.persistence.dataAccess.data.VersionChanges;

import java.time.Instant;
import java.util.Optional;

public interface Version {

    int getVersion();

    Instant getCreatedDate();

    Optional<Long> getUserId();

    VersionChanges getChanges();
}
