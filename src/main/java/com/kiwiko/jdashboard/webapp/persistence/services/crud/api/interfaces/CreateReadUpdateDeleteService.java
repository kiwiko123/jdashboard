package com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces;

import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.SimpleTransactionProvider;
import com.kiwiko.jdashboard.library.lang.reflection.merging.api.interfaces.ObjectMerger;
import com.kiwiko.jdashboard.library.lang.reflection.merging.impl.NonNullFieldMerger;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.LongDataEntity;
import com.kiwiko.jdashboard.library.persistence.data.properties.api.interfaces.DataEntityMapper;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public abstract class CreateReadUpdateDeleteService<
        Entity extends LongDataEntity,
        Dto extends DataEntityDTO,
        DataAccessObject extends com.kiwiko.jdashboard.tools.dataaccess.api.interfaces.DataAccessObject<Entity>> {
    protected final DataAccessObject dataAccessObject;
    protected final DataEntityMapper<Entity, Dto> mapper;
    private final SimpleTransactionProvider transactionProvider;
    private final ObjectMerger<Dto> dtoObjectMerger;

    protected CreateReadUpdateDeleteService(
            DataAccessObject dataAccessObject,
            DataEntityMapper<Entity, Dto> mapper,
            SimpleTransactionProvider transactionProvider) {
        this.dataAccessObject = dataAccessObject;
        this.mapper = mapper;
        this.dtoObjectMerger = new NonNullFieldMerger<>();
        this.transactionProvider = transactionProvider;
    }

    public Optional<Dto> get(Long id) {
        return transactionProvider.readOnly(() -> dataAccessObject.getById(id).map(mapper::toDto));
    }

    public Dto create(Dto obj) {
        return transactionProvider.readWrite(() -> {
            if (obj.getId() != null && dataAccessObject.getById(obj.getId()).isPresent()) {
                throw new EntityExistsException(String.format("Cannot create a new %s entity because one already exists with ID %d", getClass().getName(), obj.getId()));
            }

           Entity entityToCreate = mapper.toEntity(obj);
           Entity createdEntity = dataAccessObject.save(entityToCreate);
           return mapper.toDto(createdEntity);
        });
    }

    public Dto update(Dto obj) {
        return transactionProvider.readWrite(() -> {
            if (dataAccessObject.getById(obj.getId()).isEmpty()) {
                throw new EntityNotFoundException(String.format("No existing %s entity found with ID %d", getClass().getName(), obj.getId()));
            }

            Entity entityToUpdate = mapper.toEntity(obj);
            Entity updatedEntity = dataAccessObject.save(entityToUpdate);
            return mapper.toDto(updatedEntity);
        });
    }

    public Dto merge(Dto obj) {
        return transactionProvider.readWrite(() -> {
            Dto existingObject = dataAccessObject.getById(obj.getId())
                    .map(mapper::toDto)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("No existing %s entity found with ID %d", getClass().getName(), obj.getId())));

            Dto mergedObject = dtoObjectMerger.merge(obj, existingObject);

            Entity entityToUpdate = mapper.toEntity(mergedObject);
            Entity updatedEntity = dataAccessObject.save(entityToUpdate);
            return mapper.toDto(updatedEntity);
        });
    }

    public Dto delete(Long id) {
        return transactionProvider.readWrite(() -> {
           Entity existingEntity = dataAccessObject.getById(id)
                   .orElseThrow(() -> new EntityNotFoundException(String.format("No existing %s entity found with ID %d", getClass().getName(), id)));
           dataAccessObject.delete(existingEntity);
           return mapper.toDto(existingEntity);
        });
    }
}
