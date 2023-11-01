package com.kiwiko.jdashboard.framework.datasources.frameworkinternal;

import com.kiwiko.jdashboard.tools.dataaccess.api.AbstractEntityManagerProvider;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class FrameworkInternalEntityManagerProvider extends AbstractEntityManagerProvider {

    @PersistenceContext(unitName = FrameworkInternalDatasourceConstants.PERSISTENCE_UNIT_NAME) private EntityManager entityManager;

    @Nonnull
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
