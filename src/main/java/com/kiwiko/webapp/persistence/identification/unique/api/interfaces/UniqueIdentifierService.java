package com.kiwiko.webapp.persistence.identification.unique.api.interfaces;

import com.kiwiko.webapp.persistence.identification.unique.api.dto.UniversalUniqueIdentifier;

import java.util.Optional;

public interface UniqueIdentifierService {

    String generateUuid();

    Optional<UniversalUniqueIdentifier> getByUuid(String uuid);

    Optional<UniversalUniqueIdentifier> getByReferenceKey(String referenceKey);

    UniversalUniqueIdentifier create(UniversalUniqueIdentifier identifier);
}
