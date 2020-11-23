package com.kiwiko.library.persistence.interfaces.api;

import com.kiwiko.library.persistence.identification.Identifiable;

import java.util.Optional;

public interface CreateReadUpdateDeleteAPI<T extends Identifiable<Long>> {

    Optional<T> get(Long id);
    T create(T obj);
    T update(T obj);
    void delete(Long id);
}
