package com.kiwiko.library.persistence.interfaces.api;

import com.kiwiko.library.persistence.identification.Identifiable;

import java.util.Optional;

public interface CreateReadUpdateDeleteAPI<T extends Identifiable<Long>> {

    /**
     * Fetch a record by its ID.
     *
     * @param id the ID to fetch
     * @return the record with the matching ID, if found
     */
    Optional<T> read(long id);

    /**
     * Alias for {@link #read(long)}.
     * @see #read(long)
     */
    default Optional<T> get(long id) {
        return read(id);
    }

    /**
     * Given an object, create and persist it.
     *
     * @param obj the object to create
     * @param <R> the type of object to create
     * @return the created object
     */
    <R extends T> T create(R obj);

    /**
     * Persist the given object, replacing its existing fields. The object's ID is required.
     *
     * @param obj the object to update
     * @param <R> the type of object to update
     * @return the updated object
     */
    <R extends T> T update(R obj);

    /**
     * Delete the record associated with the given ID.
     * See implementation to determine if it's a soft or hard delete.
     *
     * @param id the ID of the record to delete
     */
    void delete(long id);
}
