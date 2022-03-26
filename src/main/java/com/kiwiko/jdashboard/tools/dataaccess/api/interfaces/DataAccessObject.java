package com.kiwiko.jdashboard.tools.dataaccess.api.interfaces;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.DataEntity;

import java.util.Optional;

public interface DataAccessObject<T extends DataEntity> {

    T save(T entity);

    void delete(T entity);

    void flush();

    Optional<T> getById(long id);

    Optional<T> getProxyById(long id);
}
