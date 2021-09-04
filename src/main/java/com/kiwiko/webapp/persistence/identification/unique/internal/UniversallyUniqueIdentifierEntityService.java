package com.kiwiko.webapp.persistence.identification.unique.internal;

import com.kiwiko.webapp.mvc.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.webapp.persistence.identification.unique.api.dto.UniversalUniqueIdentifier;
import com.kiwiko.webapp.persistence.identification.unique.api.interfaces.UniqueIdentifierService;
import com.kiwiko.webapp.persistence.identification.unique.api.interfaces.parameters.GetIdentifierByReferenceParameters;
import com.kiwiko.webapp.persistence.identification.unique.internal.data.UniversalUniqueIdentifierEntity;
import com.kiwiko.webapp.persistence.identification.unique.internal.data.UniversalUniqueIdentifierEntityDataFetcher;
import com.kiwiko.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class UniversallyUniqueIdentifierEntityService implements UniqueIdentifierService {

    @Inject private UniversalUniqueIdentifierEntityDataFetcher dataFetcher;
    @Inject private UniversalUniqueIdentifierEntityMapper mapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;
    @Inject private UuidGenerator uuidGenerator;
    @Inject private TransactionProvider transactionProvider;

    @Override
    public Optional<UniversalUniqueIdentifier> getByReference(GetIdentifierByReferenceParameters parameters) {
        Objects.requireNonNull(parameters.getReferencedTableName());
        Objects.requireNonNull(parameters.getReferencedId());

        return transactionProvider.readOnly(() -> dataFetcher.getByReference(parameters).map(mapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UniversalUniqueIdentifier> getByUuid(String uuid) {
        return dataFetcher.getByUuid(uuid).map(mapper::toDto);
    }

    @Override
    public UniversalUniqueIdentifier create(UniversalUniqueIdentifier identifier) {
        Objects.requireNonNull(identifier.getReferencedTableName(), "Referenced table is required to link a UUID");
        Objects.requireNonNull(identifier.getReferencedId(), "Referenced ID is required to link a UUID");
        String uuid = uuidGenerator.generate();

        return transactionProvider.readWrite(() -> {
            UniversalUniqueIdentifierEntity entityToCreate = mapper.toEntity(identifier);
            entityToCreate.setUuid(uuid);
            entityToCreate.setCreatedDate(Instant.now());

            UniversalUniqueIdentifierEntity createdEntity = dataFetcher.save(entityToCreate);
            return mapper.toDto(createdEntity);
        });
    }

    @Override
    public String generateUuid() {
        return uuidGenerator.generate();
    }
}
