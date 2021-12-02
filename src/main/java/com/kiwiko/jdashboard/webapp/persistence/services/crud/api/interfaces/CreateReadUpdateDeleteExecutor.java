package com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces;

import com.kiwiko.library.persistence.data.properties.api.interfaces.DataEntityMapper;
import com.kiwiko.library.persistence.data.api.interfaces.DataEntityDTO;
import com.kiwiko.jdashboard.webapp.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.jdashboard.webapp.persistence.data.fetchers.api.interfaces.EntityDataFetcher;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.internal.EntityMerger;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.internal.MergeStrategy;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class CreateReadUpdateDeleteExecutor {

    @Inject private TransactionProvider transactionProvider;
    @Inject private EntityMerger entityMerger;

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends EntityDataFetcher<Entity>,
            Mapper extends DataEntityMapper<Entity, Dto>> Optional<Dto> read(long id, DataFetcher dataFetcher, Mapper mapper) {
        return transactionProvider.readOnly(() -> dataFetcher.getById(id).map(mapper::toDto));
    }

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends EntityDataFetcher<Entity>,
            Mapper extends DataEntityMapper<Entity, Dto>> Optional<Dto> get(long id, DataFetcher dataFetcher, Mapper mapper) {
        return read(id, dataFetcher, mapper);
    }

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends EntityDataFetcher<Entity>,
            Mapper extends DataEntityMapper<Entity, Dto>> Dto create(Dto obj, DataFetcher dataFetcher, Mapper mapper) {
        return transactionProvider.readWrite(() -> {
            Entity entityToCreate = mapper.toEntity(obj);
            Entity savedEntity = dataFetcher.save(entityToCreate);
            return mapper.toDto(savedEntity);
        });
    }

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends EntityDataFetcher<Entity>,
            Mapper extends DataEntityMapper<Entity, Dto>> Dto update(Dto obj, DataFetcher dataFetcher, Mapper mapper) {
        Objects.requireNonNull(obj.getId(), "ID is required to update an existing entity");
        return transactionProvider.readWrite(() -> {
            Dto existingObject = get(obj.getId(), dataFetcher, mapper).orElse(null);
            Objects.requireNonNull(existingObject, String.format("No existing record found with ID %d: %s", obj.getId(), obj));

            Entity entityToCreate = mapper.toEntity(obj);
            Entity savedEntity = dataFetcher.save(entityToCreate);
            return mapper.toDto(savedEntity);
        });
    }

    public <Entity extends DataEntity, DataFetcher extends EntityDataFetcher<Entity>> void delete(long id, DataFetcher dataFetcher) {
        transactionProvider.readWrite(() -> {
            Entity existingRecord = dataFetcher.getById(id).orElse(null);
            Objects.requireNonNull(existingRecord, String.format("No existing record found with ID %d", id));

            dataFetcher.delete(existingRecord);
        });
    }

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends EntityDataFetcher<Entity>,
            Mapper extends DataEntityMapper<Entity, Dto>> Dto merge(Dto obj, DataFetcher dataFetcher, Mapper mapper) {
        return entityMerger.mergeFields(obj, dataFetcher, mapper, MergeStrategy.SET_NON_NULL);
    }
}
