package com.kiwiko.webapp.persistence.services.crud.api.interfaces;

import com.kiwiko.library.lang.reflection.properties.api.BidirectionalPropertyMapper;
import com.kiwiko.library.persistence.dataAccess.data.DataEntityDTO;
import com.kiwiko.webapp.mvc.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.webapp.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.webapp.persistence.data.fetchers.api.interfaces.EntityDataFetcher;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class CreateReadUpdateDeleteExecutor {

    @Inject private TransactionProvider transactionProvider;

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends EntityDataFetcher<Entity>,
            Mapper extends BidirectionalPropertyMapper<Entity, Dto>> Optional<Dto> read(long id, DataFetcher dataFetcher, Mapper mapper) {
        return transactionProvider.readOnly(() -> dataFetcher.getById(id).map(mapper::toTargetType));
    }

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends EntityDataFetcher<Entity>,
            Mapper extends BidirectionalPropertyMapper<Entity, Dto>> Optional<Dto> get(long id, DataFetcher dataFetcher, Mapper mapper) {
        return read(id, dataFetcher, mapper);
    }

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends EntityDataFetcher<Entity>,
            Mapper extends BidirectionalPropertyMapper<Entity, Dto>> Dto create(Dto obj, DataFetcher dataFetcher, Mapper mapper) {
        return transactionProvider.readWrite(() -> {
            Entity entityToCreate = mapper.toSourceType(obj);
            Entity savedEntity = dataFetcher.save(entityToCreate);
            return mapper.toTargetType(savedEntity);
        });
    }

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends EntityDataFetcher<Entity>,
            Mapper extends BidirectionalPropertyMapper<Entity, Dto>> Dto update(Dto obj, DataFetcher dataFetcher, Mapper mapper) {
        Objects.requireNonNull(obj.getId(), "ID is required to update an existing entity");
        return transactionProvider.readWrite(() -> {
            Dto existingObject = get(obj.getId(), dataFetcher, mapper).orElse(null);
            Objects.requireNonNull(existingObject, String.format("No existing record found with ID %d: %s", obj.getId(), obj));

            Entity entityToCreate = mapper.toSourceType(obj);
            Entity savedEntity = dataFetcher.save(entityToCreate);
            return mapper.toTargetType(savedEntity);
        });
    }

    public <Entity extends DataEntity, DataFetcher extends EntityDataFetcher<Entity>> void delete(long id, DataFetcher dataFetcher) {
        transactionProvider.readWrite(() -> {
            Entity existingRecord = dataFetcher.getById(id).orElse(null);
            Objects.requireNonNull(existingRecord, String.format("No existing record found with ID %d", id));

            dataFetcher.delete(existingRecord);
        });
    }
}
