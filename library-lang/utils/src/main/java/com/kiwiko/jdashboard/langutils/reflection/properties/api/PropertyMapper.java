package com.kiwiko.jdashboard.langutils.reflection.properties.api;

public interface PropertyMapper<SourceType, TargetType> {

    /**
     * Given an object, copy all of its properties to the target.
     * The target object will be modified.
     *
     * @param source the object from which fields will be copied
     * @param destination the object to which fields will be copied
     */
    void copyToTarget(SourceType source, TargetType destination);

    /**
     * Like {@link #copyToTarget(Object, Object)}, but copies the source's fields into a new instance of the target class.
     *
     * @param source the object from which fields will be copied
     * @return a new instance of the target class whose fields have been mapped from the source
     */
    TargetType toTargetType(SourceType source);
}
