package com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces;

import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;
import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntityDTO;
import com.kiwiko.jdashboard.library.persistence.data.properties.api.interfaces.DataEntityMapper;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.internal.EntityMerger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class ServiceOperationParameters<Entity extends DataEntity, Dto extends DataEntityDTO, DataAccessObject extends com.kiwiko.jdashboard.tools.dataaccess.api.interfaces.DataAccessObject<Entity>, Mapper extends DataEntityMapper<Entity, Dto>> {
    private final @Nonnull DataAccessObject dataAccessObject;
    private final @Nonnull Mapper mapper;
    private final @Nullable String dataSource;
    private final @Nonnull EntityMerger entityMerger;
    private final @Nonnull TransactionProvider transactionProvider;

    ServiceOperationParameters(@Nonnull DataAccessObject dataAccessObject, @Nonnull Mapper mapper, @Nullable String dataSource, EntityMerger entityMerger, TransactionProvider transactionProvider) {
        this.dataAccessObject = dataAccessObject;
        this.mapper = mapper;
        this.dataSource = dataSource;
        this.entityMerger = entityMerger;
        this.transactionProvider = transactionProvider;
    }

    @Nonnull
    public DataAccessObject getDataAccessObject() {
        return dataAccessObject;
    }

    @Nonnull
    public Mapper getMapper() {
        return mapper;
    }

    @Nullable
    public String getDataSource() {
        return dataSource;
    }

    @Nonnull
    EntityMerger getEntityMerger() {
        return entityMerger;
    }

    @Nonnull
    TransactionProvider getTransactionProvider() {
        return transactionProvider;
    }

    public static final class Builder<Entity extends DataEntity, Dto extends DataEntityDTO, DataAccessObject extends com.kiwiko.jdashboard.tools.dataaccess.api.interfaces.DataAccessObject<Entity>, Mapper extends DataEntityMapper<Entity, Dto>> {
        private @Nonnull DataAccessObject dataAccessObject;
        private @Nonnull Mapper mapper;
        private @Nullable String dataSource;
        private final @Nonnull EntityMerger entityMerger;
        private final @Nonnull TransactionProvider transactionProvider;

        Builder(
                EntityMerger entityMerger,
                TransactionProvider transactionProvider) {
            this.entityMerger = entityMerger;
            this.transactionProvider = transactionProvider;
        }

        public Builder<Entity, Dto, DataAccessObject, Mapper> dataAccessObject(DataAccessObject dataAccessObject) {
            this.dataAccessObject = dataAccessObject;
            return this;
        }

        public Builder<Entity, Dto, DataAccessObject, Mapper> mapper(Mapper mapper) {
            this.mapper = mapper;
            return this;
        }

        public Builder<Entity, Dto, DataAccessObject, Mapper> dataSource(String dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public ServiceOperation<Entity, Dto, DataAccessObject, Mapper> operation() {
            return new ServiceOperation<>(build());
        }

        private ServiceOperationParameters<Entity, Dto, DataAccessObject, Mapper> build() {
            Objects.requireNonNull(dataAccessObject, "Data access object is required");
            Objects.requireNonNull(mapper, "Mapper is required");
            return new ServiceOperationParameters<>(dataAccessObject, mapper, dataSource, entityMerger, transactionProvider);
        }
    }
}
