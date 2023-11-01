package com.kiwiko.jdashboard.tools.dataaccess.api.interfaces;

import com.kiwiko.jdashboard.library.persistence.data.api.interfaces.LongDataEntity;

import java.util.Optional;

public interface DataAccessObject<T extends LongDataEntity> {

    T save(T entity);

    void delete(T entity);

    void flush();

    Optional<T> getById(long id);
}
