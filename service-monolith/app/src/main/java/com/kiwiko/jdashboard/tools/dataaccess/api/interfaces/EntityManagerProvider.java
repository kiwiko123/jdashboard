package com.kiwiko.jdashboard.tools.dataaccess.api.interfaces;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;

public interface EntityManagerProvider {

    @Nonnull
    EntityManager get();
}
