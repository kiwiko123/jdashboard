package com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces;

import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.dto.UniversalUniqueIdentifier;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.parameters.CreateUuidParameters;

import java.util.Optional;

public interface UniqueIdentifierService {

    String generateUuid();

    Optional<UniversalUniqueIdentifier> getByUuid(String uuid);

    Optional<UniversalUniqueIdentifier> getByReferenceKey(String referenceKey);

    UniversalUniqueIdentifier create(UniversalUniqueIdentifier identifier);

    UniversalUniqueIdentifier create(CreateUuidParameters parameters);
}
