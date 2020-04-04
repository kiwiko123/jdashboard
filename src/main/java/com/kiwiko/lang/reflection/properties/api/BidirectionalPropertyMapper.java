package com.kiwiko.lang.reflection.properties.api;

public interface BidirectionalPropertyMapper<SourceType, TargetType> extends PropertyMapper<SourceType, TargetType> {

    /**
     * Given an object, copy all of its properties from the TargetType object (source) to the SourceType object (destination).
     * The SourceType object will be modified.
     *
     * @param source the object from which fields will be copied
     * @param destination the object to which fields will be copied
     */
    void toSource(TargetType source, SourceType destination);

    /**
     * Like {@link #toSource(Object, Object)}, but copies the source's fields into a new instance of the target class.
     *
     * @param source the object from which fields will be copied
     * @return a new instance of the SourceType (destination) class whose fields have been mapped from the TargetType object (source)
     */
    SourceType toSourceType(TargetType source);
}
