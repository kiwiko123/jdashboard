package com.kiwiko.jdashboard.framework.datasources;

import com.kiwiko.jdashboard.tools.dataaccess.api.AbstractEntityManagerProvider;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DefaultEntityManagerProvider extends AbstractEntityManagerProvider {

    @PersistenceContext private EntityManager entityManager;

    @Nonnull
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
