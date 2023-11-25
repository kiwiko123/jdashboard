package com.kiwiko.jdashboard.framework.data;

import java.util.Optional;

public interface DataAccessObject<T extends LongDataEntity> {

    T save(T entity);

    void delete(T entity);

    void flush();

    Optional<T> getById(long id);
}
