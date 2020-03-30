package com.kiwiko.persistence.properties.api;

public interface PropertyMapper<SourceType, TargetType> {

    /**
     * Given an object, copy all of its properties to the target.
     * The target object will be modified.
     *
     * @param source the object from which fields will be copied.
     * @param target the object to which fields will be copied.
     * @return the target object, whose fields have been modified.
     */
    void toTarget(SourceType source, TargetType target);
    TargetType toTargetType(SourceType source);
}
