package com.kiwiko.webapp.persistence.identification.unique.api.interfaces;

import com.kiwiko.webapp.persistence.identification.unique.api.dto.UniversalUniqueIdentifier;
import com.kiwiko.webapp.persistence.identification.unique.api.interfaces.parameters.GetIdentifierByReferenceParameters;

import java.util.Optional;

public interface UniqueIdentifierService {

    String generateUuid();

    Optional<UniversalUniqueIdentifier> getByUuid(String uuid);

    Optional<UniversalUniqueIdentifier> getByReference(GetIdentifierByReferenceParameters parameters);

    UniversalUniqueIdentifier create(UniversalUniqueIdentifier identifier);
}
