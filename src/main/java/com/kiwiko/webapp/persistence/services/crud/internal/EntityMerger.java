package com.kiwiko.webapp.persistence.services.crud.internal;

import com.kiwiko.library.lang.reflection.ReflectionHelper;
import com.kiwiko.library.lang.reflection.merging.api.interfaces.ObjectMerger;
import com.kiwiko.library.lang.reflection.merging.impl.FieldMerger;
import com.kiwiko.library.lang.reflection.merging.impl.NonNullFieldMerger;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.library.persistence.data.api.interfaces.DataEntityDTO;
import com.kiwiko.library.persistence.data.properties.api.interfaces.DataEntityMapper;
import com.kiwiko.webapp.mvc.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.webapp.persistence.data.fetchers.api.interfaces.EntityDataFetcher;

import javax.inject.Inject;
import java.util.Objects;

public class EntityMerger {

    @Inject private TransactionProvider transactionProvider;
    @Inject private ReflectionHelper reflectionHelper;
    @Inject private Logger logger;

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends EntityDataFetcher<Entity>,
            Mapper extends DataEntityMapper<Entity, Dto>> Dto mergeFields(Dto obj, DataFetcher dataFetcher, Mapper mapper, MergeStrategy mergeStrategy) {
        Objects.requireNonNull(obj.getId(), "Entity ID is required to merge/update an existing record");
        Dto objectToUpdate = transactionProvider.readOnly(() -> dataFetcher.getById(obj.getId()).map(mapper::toDto).orElse(null));
        Objects.requireNonNull(objectToUpdate, String.format("No existing record found with ID %d", obj.getId()));

        ObjectMerger<Dto> merger;

        switch (mergeStrategy.resolve()) {
            case DEFAULT:
            case SET_NON_NULL:
                merger = new NonNullFieldMerger<>();
                break;
            default:
                throw new RuntimeException("Unknown merge strategy");
        }

        merger.merge(obj, objectToUpdate);
        return transactionProvider.readWrite(() -> {
           Entity entityToSave = mapper.toEntity(objectToUpdate);
           Entity updatedEntity = dataFetcher.save(entityToSave);
           return mapper.toDto(updatedEntity);
        });
    }
}