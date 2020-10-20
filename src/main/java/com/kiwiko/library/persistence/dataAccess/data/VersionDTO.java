package com.kiwiko.library.persistence.dataAccess.data;

import com.kiwiko.library.json.data.IntermediateJsonBody;
import com.kiwiko.library.persistence.dataAccess.api.Version;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class VersionDTO implements Version {

    private int version;
    private Instant createdDate;
    private @Nullable Long userId;
    private IntermediateJsonBody changes;

    public String dump() {
        return String.format(
                "{version: %d, createdDate: %d, userId: %d, changes: %s}",
                version,
                createdDate.toEpochMilli(),
                userId,
                changes.toString());
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Optional<Long> getUserId() {
        return Optional.ofNullable(userId);
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    public IntermediateJsonBody getChanges() {
        return changes;
    }

    public void setChanges(IntermediateJsonBody changes) {
        this.changes = changes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, createdDate, userId, changes);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Version)) {
            return false;
        }

        Version other = (Version) obj;
        return Objects.equals(version, other.getVersion())
                && Objects.equals(createdDate, other.getCreatedDate())
                && Objects.equals(getUserId(), other.getUserId())
                && Objects.equals(changes, other.getChanges());
    }
}
