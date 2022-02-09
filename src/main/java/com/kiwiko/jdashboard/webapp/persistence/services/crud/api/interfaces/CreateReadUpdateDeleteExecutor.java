package com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces;

import com.kiwiko.jdashboard.library.persistence.data.properties.api.interfaces.DataEntityMapper;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;
import com.kiwiko.jdashboard.webapp.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.jdashboard.webapp.persistence.data.access.api.interfaces.DataAccessObject;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.internal.EntityMerger;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.internal.MergeStrategy;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

/**
 * Utility class to provide basic create, read, update, and delete operations for data entities that conform to
 * Jdashboard service interfaces with the following criteria:
 * <ul>
 *     <li>Data entities must extend {@link DataEntity}.</li>
 *     <li>DTOs must extend {@link DataEntityDTO}.</li>
 *     <li>Data access objects must extend {@link DataAccessObject}.</li>
 *     <li>Data entity mappers must extend {@link DataEntityMapper}.</li>
 * </ul>
 */
public class CreateReadUpdateDeleteExecutor {

    @Inject private TransactionProvider transactionProvider;
    @Inject private EntityMerger entityMerger;

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends DataAccessObject<Entity>,
            Mapper extends DataEntityMapper<Entity, Dto>> Optional<Dto> read(long id, DataFetcher dataFetcher, Mapper mapper) {
        return transactionProvider.readOnly(() -> dataFetcher.getById(id).map(mapper::toDto));
    }

    /**
     * Alias for {@link #read(long, DataAccessObject, DataEntityMapper)}.
     *
     * @see #read(long, DataAccessObject, DataEntityMapper)
     */
    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends DataAccessObject<Entity>,
            Mapper extends DataEntityMapper<Entity, Dto>> Optional<Dto> get(long id, DataFetcher dataFetcher, Mapper mapper) {
        return read(id, dataFetcher, mapper);
    }

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends DataAccessObject<Entity>,
            Mapper extends DataEntityMapper<Entity, Dto>> Dto create(Dto obj, DataFetcher dataFetcher, Mapper mapper) {
        return transactionProvider.readWrite(() -> {
            Entity entityToCreate = mapper.toEntity(obj);
            Entity savedEntity = dataFetcher.save(entityToCreate);
            return mapper.toDto(savedEntity);
        });
    }

    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends DataAccessObject<Entity>,
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

    public <Entity extends DataEntity, DataFetcher extends DataAccessObject<Entity>> void delete(long id, DataFetcher dataFetcher) {
        transactionProvider.readWrite(() -> {
            Entity existingRecord = dataFetcher.getById(id).orElse(null);
            Objects.requireNonNull(existingRecord, String.format("No existing record found with ID %d", id));

            dataFetcher.delete(existingRecord);
        });
    }

    /**
     * Merge the provided DTO into the existing record and persist changes.
     * The existing record will be retrieved from the database.
     * All fields of matching names with non-null values from the input DTO will overwrite their counterparts in the existing record.
     *
     * @return the newly updated DTO
     */
    public <Entity extends DataEntity,
            Dto extends DataEntityDTO,
            DataFetcher extends DataAccessObject<Entity>,
            Mapper extends DataEntityMapper<Entity, Dto>> Dto merge(Dto obj, DataFetcher dataFetcher, Mapper mapper) {
        return entityMerger.mergeFields(obj, dataFetcher, mapper, MergeStrategy.SET_NON_NULL);
    }
}
