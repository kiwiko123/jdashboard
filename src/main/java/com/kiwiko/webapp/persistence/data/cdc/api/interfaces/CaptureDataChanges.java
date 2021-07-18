package com.kiwiko.webapp.persistence.data.cdc.api.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Automatically record data changes when a record in a database table is updated via its JPA entity.
 * To enroll a data entity, do the following:
 * <ol>
 *     <li>Annotate the JPA {@link javax.persistence.Entity} class with {@literal @CaptureDataChanges}</li>.
 *     <li>The entity's manager must extend {@link com.kiwiko.webapp.persistence.data.fetchers.api.interfaces.EntityDataFetcher}</li>.
 * </ol>
 *
 * Record updates will be recorded as {@link com.kiwiko.webapp.persistence.data.versions.api.dto.TableRecordVersion}s.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CaptureDataChanges {
}
