package com.kiwiko.jdashboard.webapp.persistence.services.crud.internal;

import com.kiwiko.jdashboard.library.lang.reflection.ReflectionHelper;
import com.kiwiko.jdashboard.library.lang.reflection.merging.api.interfaces.ObjectMerger;
import com.kiwiko.jdashboard.library.lang.reflection.merging.api.interfaces.ObjectMergingException;
import com.kiwiko.jdashboard.library.lang.reflection.merging.impl.NonNullFieldMerger;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;
import com.kiwiko.jdashboard.library.persistence.data.properties.api.interfaces.DataEntityMapper;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.tools.dataaccess.impl.JpaDataAccessObject;

import javax.inject.Inject;
import java.util.Objects;

public class EntityMerger {

    @Inject private TransactionProvider transactionProvider;
    @Inject private ReflectionHelper reflectionHelper;
    @Inject private Logger logger;

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends JpaDataAccessObject<Entity>,
            Mapper extends DataEntityMapper<Entity, Dto>> Dto mergeFields(Dto obj, DataFetcher dataFetcher, Mapper mapper, MergeStrategy mergeStrategy) {
        Objects.requireNonNull(obj.getId(), "Entity ID is required to merge/update an existing record");
        Dto objectToUpdate = transactionProvider.readOnly(
                () -> dataFetcher.getById(obj.getId()).map(mapper::toDto).orElse(null));
        Objects.requireNonNull(objectToUpdate, String.format("No existing record found with ID %d", obj.getId()));

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
}
