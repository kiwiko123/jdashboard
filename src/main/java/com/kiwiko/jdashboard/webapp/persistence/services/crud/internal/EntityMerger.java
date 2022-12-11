package com.kiwiko.jdashboard.webapp.persistence.services.crud.internal;

import com.kiwiko.jdashboard.library.lang.reflection.merging.api.interfaces.ObjectMerger;
import com.kiwiko.jdashboard.library.lang.reflection.merging.api.interfaces.ObjectMergingException;
import com.kiwiko.jdashboard.library.lang.reflection.merging.impl.NonNullFieldMerger;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;
import com.kiwiko.jdashboard.library.persistence.data.properties.api.interfaces.DataEntityMapper;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.tools.dataaccess.impl.JpaDataAccessObject;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class EntityMerger {

    @Inject private TransactionProvider transactionProvider;

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends JpaDataAccessObject<Entity>,
            Mapper extends DataEntityMapper<Entity, Dto>> Dto mergeFields(Dto obj, DataFetcher dataFetcher, Mapper mapper, MergeStrategy mergeStrategy) {
        Objects.requireNonNull(obj.getId(), "Entity ID is required to merge/update an existing record");
        Dto objectToUpdate = transactionProvider.readOnly(
                () -> dataFetcher.getById(obj.getId()).map(mapper::toDto).orElse(null));
        if (objectToUpdate == null) {
            throw new EntityNotFoundException(String.format("No existing record found with ID %d", obj.getId()));
        }

        ObjectMerger<Dto> merger;

        switch (mergeStrategy.resolve()) {
            case DEFAULT:
            case SET_NON_NULL:
                merger = new NonNullFieldMerger<>();
                break;
            default:
                throw new ObjectMergingException("Unknown merge strategy");
        }

        merger.merge(obj, objectToUpdate);
        return transactionProvider.readWrite(
                () -> {
                    Entity entityToSave = mapper.toEntity(objectToUpdate);
                    Entity updatedEntity = dataFetcher.save(entityToSave);
                    return mapper.toDto(updatedEntity);
                });
    }

    public <Dto extends DataEntityDTO> Dto mergeFields(
                    Dto obj,
                    Supplier<Dto> getExistingRecord,
                    Function<Dto, Dto> save,
                    MergeStrategy mergeStrategy) {
        Dto objectToUpdate = getExistingRecord.get();
        Objects.requireNonNull(objectToUpdate, "Input object is required to merge");

        ObjectMerger<Dto> merger = switch (mergeStrategy.resolve()) {
            case DEFAULT, SET_NON_NULL -> new NonNullFieldMerger<>();
            default -> throw new ObjectMergingException("Unknown merge strategy");
        };

        merger.merge(obj, objectToUpdate);
        return save.apply(objectToUpdate);
    }
}
