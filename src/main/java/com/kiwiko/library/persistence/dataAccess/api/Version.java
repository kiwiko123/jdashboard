package com.kiwiko.library.persistence.dataAccess.api;

import com.kiwiko.library.json.data.IntermediateJsonBody;

import java.time.Instant;
import java.util.Optional;

public interface Version {

    int getVersion();

    Instant getCreatedDate();

    Optional<Long> getUserId();

    IntermediateJsonBody getChanges();
}
