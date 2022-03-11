package com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces;

import com.kiwiko.jdashboard.webapp.persistence.data.access.api.interfaces.DataAccessObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Automatically record data changes when a record in a database table is updated via its JPA entity.
 * To enroll a data entity, do the following:
 * <ol>
 *     <li>Annotate the JPA {@link javax.persistence.Entity} class with {@literal @CaptureDataChanges}</li>.
 *     <li>The entity's manager must extend {@link DataAccessObject}</li>.
 * </ol>
 *
 * Record updates will be recorded as {@link com.kiwiko.jdashboard.webapp.persistence.data.versions.api.dto.TableRecordVersion}s.
 *
 * @see com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CaptureDataChanges {

    /**
     * If true, throw a {@link com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.exceptions.CaptureEntityDataChangeException} on failure.
     * This may roll back the database transaction.
     * Otherwise, allow the transaction to proceed without recording the data change.
     *
     * @return true to throw an exception on failure, or false otherwise
     */
    boolean exceptionOnFailure() default true;
}
