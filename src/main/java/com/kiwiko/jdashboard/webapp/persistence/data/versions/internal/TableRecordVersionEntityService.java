package com.kiwiko.jdashboard.webapp.persistence.data.versions.internal;

import com.kiwiko.jdashboard.framework.persistence.transactions.api.interfaces.TransactionProvider;
import com.kiwiko.jdashboard.webapp.persistence.data.versions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.webapp.persistence.data.versions.api.interfaces.TableRecordVersionService;
import com.kiwiko.jdashboard.webapp.persistence.data.versions.api.interfaces.parameters.GetTableRecordVersions;
import com.kiwiko.jdashboard.webapp.persistence.data.versions.internal.data.TableRecordVersionEntity;
import com.kiwiko.jdashboard.webapp.persistence.data.versions.internal.data.TableRecordVersionEntityDataFetcher;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

public class TableRecordVersionEntityService implements TableRecordVersionService {

    @Inject private TableRecordVersionEntityDataFetcher dataFetcher;
    @Inject private TableRecordVersionEntityMapper mapper;
    @Inject private TransactionProvider transactionProvider;

    @Override
    public LinkedList<TableRecordVersion> getVersions(GetTableRecordVersions query) {
        return transactionProvider.readOnly(() -> {
           return dataFetcher.getByQuery(query).stream()
                   .map(mapper::toDTO)
                   .collect(Collectors.collectingAndThen(Collectors.toList(), LinkedList::new));
        });
    }

    @Override
    public Optional<TableRecordVersion> get(long id) {
        return transactionProvider.readOnly(() -> dataFetcher.getById(id).map(mapper::toDTO));
    }

    @Override
    public TableRecordVersion create(TableRecordVersion version) {
        return transactionProvider.readWrite(() -> {
            TableRecordVersionEntity entity = mapper.toEntity(version);
            entity = dataFetcher.save(entity);
            return mapper.toDTO(entity);
        });
    }
}
