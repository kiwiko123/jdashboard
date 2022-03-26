package com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces;

import com.kiwiko.jdashboard.webapp.persistence.data.access.api.interfaces.DataAccessObject;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.exceptions.EntityChangeDataCaptureException;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.EntityChangeDataCapturer;

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
 * Record updates will be recorded as {@link com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion}s.
 *
 * @see EntityChangeDataCapturer
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CaptureDataChanges {

    /**
     * Describes the confidence level that the change data capture operation will be completed.
     *
     * {@link SuccessConfidenceLevel#CONFIDENT} indicates that the CDC operation's result will be known. This may take
     * longer, but offers idempotency. If the operation fails, then a {@link EntityChangeDataCaptureException} will be
     * thrown and the database transaction will be rolled back.
     *
     * {@link SuccessConfidenceLevel#OPTIMISTIC} indicates that the CDC operation will occur independently of the
     * database transaction. The process may be faster, and the CDC operation's completion state will not affect the
     * database transaction. This can possibly result in missed data changes.
     *
     * @return the confidence level
     */
    SuccessConfidenceLevel successConfidence() default SuccessConfidenceLevel.CONFIDENT;
}
