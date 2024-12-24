package com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.data;

import com.kiwiko.jdashboard.tools.dataaccess.impl.JpaDataAccessObject;
import com.kiwiko.jdashboard.webapp.apps.grocerylist.api.dto.QueryGroceryListsInput;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class GroceryListEntityDataAccessObject extends JpaDataAccessObject<GroceryListEntity> {

    public List<GroceryListEntity> query(QueryGroceryListsInput input) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<GroceryListEntity> query = builder.createQuery(entityType);
        Root<GroceryListEntity> root = query.from(entityType);

        Predicate allCriteria = builder.and();

        if (input.getIds() != null) {
            Expression<Long> id = root.get("id");
            Predicate hasId = id.in(input.getIds());
            allCriteria = builder.and(allCriteria, hasId);
        }

        if (input.getUserIds() != null) {
            Expression<Long> userId = root.get("userId");
            Predicate hasUserId = userId.in(input.getUserIds());
            allCriteria = builder.and(allCriteria, hasUserId);
        }

        if (input.getNames() != null) {
            Expression<String> name = root.get("name");
            Predicate hasName = name.in(input.getNames());
            allCriteria = builder.and(allCriteria, hasName);
        }

        if (input.getIsRemoved() != null) {
            Expression<Boolean> isRemoved = root.get("isRemoved");
            Predicate isRemovedPredicate = input.getIsRemoved() ? builder.isTrue(isRemoved) : builder.isFalse(isRemoved);
            allCriteria = builder.and(allCriteria, isRemovedPredicate);
        }

        query.where(allCriteria);
        return createQuery(query).getResultList();
    }
}
