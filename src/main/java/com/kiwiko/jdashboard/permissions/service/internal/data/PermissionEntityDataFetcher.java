package com.kiwiko.jdashboard.permissions.service.internal.data;

import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.tools.dataaccess.impl.JpaDataAccessObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class PermissionEntityDataFetcher extends JpaDataAccessObject<PermissionEntity> {

    public List<PermissionEntity> query(QueryPermissionsInput input) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<PermissionEntity> query = builder.createQuery(entityType);
        Root<PermissionEntity> root = query.from(entityType);

        Predicate allCriteria = builder.and();

        if (input.getUserIds() != null) {
            Expression<Long> userId = root.get("userId");
            Predicate hasUserId = userId.in(input.getUserIds());
            allCriteria = builder.and(allCriteria, hasUserId);
        }

        if (input.getPermissionNames() != null) {
            Expression<String> permissionName = root.get("permissionName");
            Predicate hasPermissionName = permissionName.in(input.getPermissionNames());
            allCriteria = builder.and(allCriteria, hasPermissionName);
        }

        query.where(allCriteria);

        return createQuery(query).getResultList();
    }
}
