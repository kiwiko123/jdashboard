package com.kiwiko.jdashboard.tablerecordversions.service.internal.data;

import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.GetLastUpdatedInput;
import com.kiwiko.jdashboard.tablerecordversions.service.api.interfaces.parameters.GetTableRecordVersions;
import com.kiwiko.jdashboard.tools.dataaccess.impl.JpaDataAccessObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class TableRecordVersionEntityDataFetcher extends JpaDataAccessObject<TableRecordVersionEntity> {

    @Override
    public TableRecordVersionEntity save(TableRecordVersionEntity entity) {
        if (entity.getId() == null) {
            entity.setCreatedDate(Instant.now());
        }
        return super.save(entity);
    }

    public List<TableRecordVersionEntity> getByQuery(GetTableRecordVersions parameters) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<TableRecordVersionEntity> query = builder.createQuery(entityType);
        Root<TableRecordVersionEntity> root = query.from(entityType);

        Expression<String> tableNameField = root.get("tableName");
        Expression<Long> recordIdField = root.get("recordId");
        Expression<Instant> createdDateField = root.get("createdDate");

        Predicate matchesTableName = builder.equal(tableNameField, parameters.getTableName());
        Predicate matchesRecordId = builder.equal(recordIdField, parameters.getRecordId());

        Order ascendingCreatedDate = builder.asc(createdDateField);

        query.where(matchesTableName, matchesRecordId);
        query.orderBy(ascendingCreatedDate);

        return createQuery(query).getResultList();
    }

    public List<TableRecordVersionEntity> getLastUpdated(GetLastUpdatedInput input) {
        String queryString = input.getVersionRecords().stream()
                .map(versionRecord -> new StringBuilder("(")
                        .append("SELECT * FROM table_record_versions ")
                        .append("WHERE table_name = '").append(versionRecord.getTableName()).append('\'')
                        .append(" AND record_id = ").append(versionRecord.getId()).append(' ')
                        .append("ORDER BY created_date DESC ")
                        .append("LIMIT 1")
                        .append(')')
                        .toString())
                .collect(Collectors.joining(" UNION ")) + ';';

        @SuppressWarnings("unchecked")
        List<TableRecordVersionEntity> result = createNativeQuery(queryString).getResultList();
        return result;
    }
}
