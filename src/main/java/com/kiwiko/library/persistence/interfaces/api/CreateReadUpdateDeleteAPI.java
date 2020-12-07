package com.kiwiko.library.persistence.interfaces.api;

import com.kiwiko.library.persistence.identification.Identifiable;

import java.util.Optional;

public interface CreateReadUpdateDeleteAPI<T extends Identifiable<Long>> {

    Optional<T> get(long id);
    Optional<T> read(long id);

    <R extends T> T create(R obj);

    <R extends T> T update(R obj);

    void delete(long id);
}
