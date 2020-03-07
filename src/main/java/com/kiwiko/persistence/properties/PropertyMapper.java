package com.kiwiko.persistence.properties;

public interface PropertyMapper<SourceType, TargetType> {

    /**
     * Given an object, copy all fields, regardless of access, to the target.
     * The target object will be modified.
     *
     * @param source the object from which fields will be copied.
     * @param target the object to which fields will be copied.
     * @return the target object, whose fields have been modified.
     */
    TargetType toTargetType(SourceType source, TargetType target);
}
