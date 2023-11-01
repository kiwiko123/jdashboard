package com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces;

import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.LongDataEntity;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;
import com.kiwiko.jdashboard.library.persistence.data.properties.api.interfaces.DataEntityMapper;
import com.kiwiko.jdashboard.tools.dataaccess.impl.JpaDataAccessObject;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.internal.EntityMerger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public final class ServiceOperationParametersBuilder<Entity extends LongDataEntity, Dto extends DataEntityDTO, Dao extends JpaDataAccessObject<Entity>, Mapper extends DataEntityMapper<Entity, Dto>> {
    private @Nonnull
    Dao dataAccessObject;
    private @Nonnull Mapper mapper;
    private @Nullable String dataSource;
    private final @Nonnull EntityMerger entityMerger;
    private final @Nonnull TransactionProvider transactionProvider;

    ServiceOperationParametersBuilder(
            EntityMerger entityMerger,
            TransactionProvider transactionProvider) {
        this.entityMerger = entityMerger;
        this.transactionProvider = transactionProvider;
    }

    public ServiceOperationParametersBuilder<Entity, Dto, Dao, Mapper> dataAccessObject(Dao dataAccessObject) {
        this.dataAccessObject = dataAccessObject;
        return this;
    }

    public ServiceOperationParametersBuilder<Entity, Dto, Dao, Mapper> mapper(Mapper mapper) {
        this.mapper = mapper;
        return this;
    }

    public ServiceOperationParametersBuilder<Entity, Dto, Dao, Mapper> dataSource(String dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public ServiceOperation<Entity, Dto, Dao, Mapper> operation() {
        return new ServiceOperation<>(build());
    }

    private ServiceOperationParameters<Entity, Dto, Dao, Mapper> build() {
        Objects.requireNonNull(dataAccessObject, "Data access object is required");
        Objects.requireNonNull(mapper, "Mapper is required");
        return new ServiceOperationParameters<>(dataAccessObject, mapper, dataSource, entityMerger, transactionProvider);
    }
}
