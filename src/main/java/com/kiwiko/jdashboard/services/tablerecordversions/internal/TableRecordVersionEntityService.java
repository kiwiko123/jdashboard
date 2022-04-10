package com.kiwiko.jdashboard.services.tablerecordversions.internal;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetLastUpdatedInput;
import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.TableRecordVersionService;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.parameters.GetTableRecordVersions;
import com.kiwiko.jdashboard.services.tablerecordversions.internal.data.TableRecordVersionEntity;
import com.kiwiko.jdashboard.services.tablerecordversions.internal.data.TableRecordVersionEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TableRecordVersionEntityService implements TableRecordVersionService {

    @Inject private TableRecordVersionEntityDataFetcher dataFetcher;
    @Inject private TableRecordVersionEntityMapper mapper;
    @Inject private TransactionProvider transactionProvider;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;

    @Override
    public LinkedList<TableRecordVersion> getVersions(GetTableRecordVersions query) {
        return transactionProvider.readOnly(() -> {
           return dataFetcher.getByQuery(query).stream()
                   .map(mapper::toDto)
                   .collect(Collectors.collectingAndThen(Collectors.toList(), LinkedList::new));
        });
    }

    @Override
    public Set<TableRecordVersion> getLastUpdated(GetLastUpdatedInput input) {
        return transactionProvider.readOnly(() -> dataFetcher.getLastUpdated(input).stream()
                .map(mapper::toDto)
                .collect(Collectors.toSet()));
    }

    @Override
    public Optional<TableRecordVersion> get(long id) {
        return crudExecutor.get(id, dataFetcher, mapper);
    }

    @Override
    public TableRecordVersion create(TableRecordVersion version) {
        return transactionProvider.readWrite(() -> {
            TableRecordVersionEntity entity = mapper.toEntity(version);
            entity.setCreatedDate(Instant.now());
            entity = dataFetcher.save(entity);
            return mapper.toDto(entity);
        });
    }
}
