package com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces;

import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;
import com.kiwiko.jdashboard.library.persistence.data.properties.api.interfaces.DataEntityMapper;
import com.kiwiko.jdashboard.tools.dataaccess.impl.JpaDataAccessObject;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.internal.EntityMerger;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.internal.MergeStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class ServiceOperation<Entity extends DataEntity, Dto extends DataEntityDTO, DataAccessObject extends com.kiwiko.jdashboard.tools.dataaccess.api.interfaces.DataAccessObject<Entity>, Mapper extends DataEntityMapper<Entity, Dto>> {
    private final @Nonnull DataAccessObject dataAccessObject;
    private final @Nonnull Mapper mapper;
    private final @Nullable String dataSource;
    private final @Nonnull EntityMerger entityMerger;
    private final @Nonnull TransactionProvider transactionProvider;

    ServiceOperation(ServiceOperationParameters<Entity, Dto, DataAccessObject, Mapper> serviceOperationParameters) {
        dataAccessObject = serviceOperationParameters.getDataAccessObject();
        mapper = serviceOperationParameters.getMapper();
        dataSource = serviceOperationParameters.getDataSource();
        entityMerger = serviceOperationParameters.getEntityMerger();
        transactionProvider = serviceOperationParameters.getTransactionProvider();
    }

    public Optional<Dto> get(long id) {
        return transactionProvider.readOnly(dataSource, () -> dataAccessObject.getById(id).map(mapper::toDto));
    }

    public Dto create(@Nonnull Dto obj) throws EntityExistsException {
        return transactionProvider.readWrite(
                dataSource,
                () -> {
                    Entity entityToCreate = mapper.toEntity(obj);
                    Entity savedEntity = dataAccessObject.save(entityToCreate);
                    return mapper.toDto(savedEntity);
                });
    }

    public Dto update(@Nonnull Dto obj) throws EntityNotFoundException {
        Objects.requireNonNull(obj.getId(), "ID is required to update an existing entity");
        return transactionProvider.readWrite(
                () -> {
                    Dto existingObject = get(obj.getId()).orElse(null);
                    if (existingObject == null) {
                        throw new EntityNotFoundException(String.format("No existing record found with ID %d: %s", obj.getId(), obj));
                    }

                    Entity entityToCreate = mapper.toEntity(obj);
                    Entity savedEntity = dataAccessObject.save(entityToCreate);
                    return mapper.toDto(savedEntity);
                });
    }

    public Dto merge(@Nonnull Dto obj) throws EntityNotFoundException {
        Supplier<Dto> getExistingRecord = () -> Optional.ofNullable(obj.getId())
                .flatMap(this::get)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No existing entity found for %s", obj)));

        return entityMerger.mergeFields(
                obj,
                getExistingRecord,
                this::update,
                MergeStrategy.DEFAULT);
    }

    public Dto delete(long id) throws EntityNotFoundException {
        return transactionProvider.readWrite(
                dataSource,
                () -> {
                    Entity existingRecord = dataAccessObject.getById(id)
                            .orElseThrow(() -> new EntityNotFoundException(String.format("No existing record found with ID %d", id)));
                    Dto obj = mapper.toDto(existingRecord);

                    dataAccessObject.delete(existingRecord);
                    return obj;
                });
    }
}
