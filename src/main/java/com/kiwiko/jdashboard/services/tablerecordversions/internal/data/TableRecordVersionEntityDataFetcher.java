package com.kiwiko.jdashboard.services.tablerecordversions.internal.data;

import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.parameters.GetTableRecordVersions;
import com.kiwiko.jdashboard.webapp.persistence.data.access.api.interfaces.DataAccessObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.List;

public class TableRecordVersionEntityDataFetcher extends DataAccessObject<TableRecordVersionEntity> {

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
}
